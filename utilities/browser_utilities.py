import netifaces as ni
import pcapy

from utilities import packet_utilities
from impacket.ImpactDecoder import *
from constants import constants
from datetime import datetime
from database import queries


# a map that contains packets and the
# packets' recorded arrival times in
# which they were sniffed
sniffed_data = {}


def get_network_interface():
    """
    Gets the current interface in which
    the network connection is passing through.

    :return: the interface that is connected
    to the network (e.g. en0, en1, etc.)
    """
    network_interface = None
    interfaces = ni.interfaces()
    interface_address_dict = {}

    for interface in interfaces:
        address = [i['addr'] for i in ni.ifaddresses(interface).setdefault(ni.AF_INET, [{'addr': ''}])][0]
        interface_address_dict[interface] = address

    for interface, address in interface_address_dict.iteritems():
        if not address or address == '127.0.0.1':
            continue

        network_interface = interface

    return network_interface


def format_packet(unformatted_packet, keyword):
    """
    Formats a packet by removing any unnecessary
    data that's not associated with the keyword.
    It will first remove all hex values from the
    packet, and then it will strip data that doesn't
    pertain to the passed in keyword parameter.

    :param unformatted_packet: the untouched packet
    :param keyword: a string that determines what
    data to keep when the packet is formatted
    (e.g., GET, HOST, REFERER)
    :return: a string that can be used to more
    easily parse useful and relevant packet data
    """
    no_hex_packet = packet_utilities.remove_hex_values_from_packet(unformatted_packet)
    formatted_packet = packet_utilities.parse_packet_data_by_keyword(no_hex_packet, keyword)

    return formatted_packet


def create_packet_dict(packet_arrival_time, packet):
    """
    Creates a map that contains the packet's
    recorded arrival time in which the packet
    was sniffed, and also the packet itself.


    :param packet_arrival_time: an object that
    contains the date of the packets recorded
    arrival time (the key)
    :param packet: the packet itself (the value)
    :return: a map that contains formatted
    versions of the packet and the date of the packet
    """
    packet_dict = {}
    get_host_referer_values = []

    for keyword in constants.packet_keywords:
        get_host_referer_values.append(format_packet(packet, keyword))

    # format the time in a more recognizable format
    packet_arrival_time = datetime.strftime(packet_arrival_time, '%m-%d-%Y %H:%M:%S')
    packet_dict[packet_arrival_time] = get_host_referer_values

    return packet_dict


def remove_redundant_packets(packets):
    """
    Removes packets that contain similar to
    the same data of another packet that arrived
    at the same time. The assumption is that if
    the packet arrived at the same time, then
    it must relatively be the same or similar
    info. If any packets have the same timestamp,
    then only the first one in the list will stay.

    :param packets: the list of packets
    :return: a list of packets that contains
    no redundant data
    """
    timestamp_list = []
    packet_counter = 0

    for packet in packets:
        for timestamp in packet:
            if timestamp not in timestamp_list:
                timestamp_list.append(timestamp)
            else:
                del packets[packet_counter]

        packet_counter += 1

    return packets


def insert_packets_into_database(objectionable_packets):
    """
    Calls the query that inserts each packet into
    the database.

    :param objectionable_packets: a list that contains
    maps of packet data
    :return:
    """
    queries.insert_packets(remove_redundant_packets(objectionable_packets))


def is_packet_from_whitelisted_website(packet):
    """
    Checks to see if a packet originated from
    a whitelisted website.

    :param packet:
    :return: True if the packet came from a
    whitelisted website; False if not
    """
    for site in constants.whitelisted_websites:
        if site in str(packet):
            return True

    return False


def store_packets(pkt_header, data):
    """
    Called when the packet sniffer is active,
    this method takes the data gathered over
    the network and stores it in a map.

    :param pkt_header: the header of the packet;
    this will contain the arrival time of the
    packet itself
    :param data: the packet before it has been
    decoded
    :return: a map with the key as the timestamp
    of the packet and the value as the packet
    itself
    """
    global sniffed_data

    packet = EthDecoder().decode(data)
    packet_arrival_time = pkt_header.getts()
    sniffed_data[packet_arrival_time] = packet


def sniff_packets(packet_num):
    """
    Creates a packet sniffer object that will perform the scan
    of packets over the network.
    :param packet_num: the maximum number of packets to capture
    :return:
    """
    interface = get_network_interface()
    max_bytes = 1024
    promiscuous_mode = False
    read_timeout = 30
    packet_sniffer = pcapy.open_live(interface, max_bytes, promiscuous_mode, read_timeout)

    number_of_packets_to_capture = packet_num

    try:
        packet_sniffer.loop(number_of_packets_to_capture, store_packets)
    except:
        return


def scan_user_internet_traffic():
    """
    Scans the network traffic of the user
    and saves any objectionable content
    into the database.

    :return:
    """
    objectionable_packets = []
    number_of_packets_to_capture = 1000

    sniff_packets(number_of_packets_to_capture)

    for packet_arrival_time, packet in sniffed_data.iteritems():
        if is_packet_from_whitelisted_website(packet):
            continue

        arrival_time = datetime.fromtimestamp(packet_arrival_time[0])
        objectionable_word_found = False

        for keyword in constants.packet_keywords:
            if keyword in str(packet):
                for obj_word in constants.objectionable_words_list:
                    if obj_word.lower() in str(packet).lower():
                        objectionable_word_found = True
                        objectionable_packets.append(create_packet_dict(arrival_time, str(packet)))
                        break

            if objectionable_word_found:
                break

    if objectionable_packets:
        insert_packets_into_database(objectionable_packets)
        del objectionable_packets[:]

    if sniffed_data:
        sniffed_data.clear()
