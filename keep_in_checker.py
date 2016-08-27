import random
import threading

from utilities import browser_utilities, email_utilities
from utilities.security_utilities import decrypt
from constants import constants
from datetime import datetime
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


def send_scheduled_email():
    """
    Sends an activity report email to the
    user's accountability partners. It will
    send an email based at a frequency
    determined by the user (e.g. daily or
    weekly).

    :return:
    """
    email_frequency = 1 if constants.current_user['EmailFrequency'] == 'Daily' else 7
    time_of_last_email_sent_to_now = datetime.now() - email_utilities.date_last_email_was_sent

    if time_of_last_email_sent_to_now.days == email_frequency:
        email_utilities.has_email_been_sent = False

    if not email_utilities.has_email_been_sent:
        username = decrypt(constants.current_user['UserName'])
        user_email = decrypt(constants.current_user['UserEmail'])
        try:
            user_email_password = decrypt(constants.current_user['UserEmailPassword'])
        except:
            # there's been times when the UserEmailPassword value needs to
            # be doubly decrypted; as to why, I'm not sure yet
            user_email_password = decrypt(decrypt(constants.current_user['UserEmailPassword']))
        partner_emails = decrypt(constants.current_user['PartnerEmails'])

        email_utilities.send_email(username, user_email, user_email_password, partner_emails)

        email_utilities.has_email_been_sent = True
        email_utilities.date_last_email_was_sent = datetime.now()


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

        if not browser_utilities.is_browser_open() or not constants.current_user:
            return

        record_network_traffic()

        try:
            send_scheduled_email()
        except:
            pass
