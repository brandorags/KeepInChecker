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


import threading

from utilities import browser_utilities, email_utilities
from database.db_session import DbSession
from constants import constants
from database import queries
from time import sleep


# thread object that's used
# to scan network traffic
sniffer_thread = threading.Thread()


def initialize_database_connection():
    """
    Initializes the connection to the
    database and will create a new
    database with the User and Packet
    tables if one does not already exist.
    """
    db = DbSession(constants.database_path)
    db.create_tables_if_none_exist()
    db.commit_and_close()


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
    initialize_database_connection()
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
