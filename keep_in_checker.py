import threading

from utilities import browser_utilities, email_utilities
from constants import constants
from database import queries
from time import sleep


# thread object that's used
# to scan network traffic
sniffer_thread = threading.Thread()


def initialize_current_user():
    """
    Gets the data about the current
    user and sets it to a constant
    object that can be used to
    verify and retrieve the user's
    credentials without having to
    make calls to the database.

    :return:
    """
    user = queries.get_current_user()
    if user:
        constants.current_user = user


def record_network_traffic():
    """
    Creates the thread which
    sniffs network traffic.

    :return:
    """
    global sniffer_thread

    if not sniffer_thread.isAlive():
        sniffer_thread = threading.Thread(target=browser_utilities.scan_user_internet_traffic)
        sniffer_thread.start()


def main():
    """
    The main method. This contains a
    constant loop that scans internet
    traffic and send emails to the
    user's accountability partners.

    :return:
    """
    initialize_current_user()

    while True:
        sleep(10)

        if not constants.current_user:
            return

        record_network_traffic()

        try:
            email_utilities.send_scheduled_email()
        except:
            pass
