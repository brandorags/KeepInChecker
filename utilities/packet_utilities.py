# Copyright (c) 2016 Brandon Ragsdale
#
# This file is part of KeepInChecker.
#
# KeepInChecker is free software: you can redistribute it and/or modify
# it under the terms of the GNU General Public License as published by
# the Free Software Foundation, either version 3 of the License, or
# (at your option) any later version.
#
# KeepInChecker is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty
# of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
# GNU General Public License for more details.
#
# You should have received a copy of the GNU General Public License
# along with KeepInChecker. If not, see <http://www.gnu.org/licenses/>.


def remove_hex_values_from_packet(packet):
    """
    Splits the packet and removes all
    hex values from the packet via a
    known pattern of how the packet is
    formatted.

    :param packet: the packet in which
    to remove the hex values
    :return: a packet with no hex values
    """
    final_packet_list = []
    temp_packet_list = packet.split('\n')
    for value in temp_packet_list:
        try:
            final_packet_list.append(value.split('    ')[1].strip('\n'))
        except:
            pass

    packet_text_only = ''
    for value in final_packet_list:
        packet_text_only += value

    return packet_text_only


def parse_packet_data_by_keyword(packet, keyword):
    """
    Removes all of the data that's not
    relevant to the keyword parameter.

    :param packet: the packet in which
    to remove the irrelevant data
    :param keyword: the keyword that
    tells the method what to keep
    (e.g., the keyword would be 'GET',
    'HOST' or 'REFERER')
    :return: a packet with data that's
    relevant to the keyword parameter;
    an empty string if the parsing fails
    """
    try:
        if keyword != 'GET':
            return packet.split(keyword + ': ')[1].split('..')[0]

        return packet.split(keyword + ' ')[1].split(' ')[0]
    except:
        return ''
