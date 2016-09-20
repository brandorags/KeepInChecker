import threading
import random

from utilities import browser_utilities, email_utilities
from constants import constants
from database import queries
from time import sleep


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
        interval = random.randint(60, 240)
        sleep(interval)

        if not constants.current_user:
            return

        record_network_traffic()

        try:
            email_utilities.send_scheduled_email()
        except:
            pass
