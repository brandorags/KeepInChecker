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


import unittest
import time
import os

from utilities.security_utilities import decrypt
from database.db_session import DbSession
from constants import constants
from database import queries


class QueriesTest(unittest.TestCase):

    def setUp(self):
        constants.database_path = 'test.sqlite'

        self.user_name = 'TestUser'
        self.user_email = 'test@test.com'
        self.user_email_password = '/.9D-r$8=CP4LB<@3;+e'
        self.partner_emails = 'person1@test.com,person2@test.com'
        self.email_frequency = 'Daily'

        self.db = DbSession(constants.database_path)
        self.db.create_tables_if_none_exist()
        self.db.commit_and_close()

    def tearDown(self):
        os.remove(constants.database_path)

    def test_save_user_data(self):
        queries.save_user_data(self.user_name, self.user_email, self.user_email_password,
                               self.partner_emails, self.email_frequency)

        self.db = DbSession(constants.database_path)
        self.db.cursor.execute('SELECT * FROM User')

        user_data = self.db.cursor.fetchall()

        self.db.commit_and_close()

        self.assertEqual(len(user_data), 1, 'Only one row should exist in the table')

    def test_update_user_data(self):
        # save data first so we can update next
        queries.save_user_data(self.user_name, self.user_email, self.user_email_password,
                               self.partner_emails, self.email_frequency)

        self.user_name = 'TestNewUser'
        self.partner_emails = 'person1new@test.com,person2new@test.com'
        self.email_frequency = 'Weekly'

        queries.update_user_data(constants.current_user, self.user_name, self.user_email, self.user_email_password,
                                 self.partner_emails, self.email_frequency)

        updated_user_name = decrypt(constants.current_user['UserName'])
        self.assertEqual(self.user_name, updated_user_name, 'Value should have been updated')

        updated_partner_emails = decrypt(constants.current_user['PartnerEmails'])
        self.assertEqual(self.partner_emails, updated_partner_emails, 'Value should have been updated')

        updated_email_frequency = decrypt(constants.current_user['EmailFrequency'])
        self.assertEqual(self.email_frequency, updated_email_frequency, 'Value should have been updated')

    def test_insert_email_last_sent_date(self):
        queries.save_user_data(self.user_name, self.user_email, self.user_email_password,
                               self.partner_emails, self.email_frequency)

        email_last_sent_date = time.time()
        queries.insert_email_last_sent_date(email_last_sent_date)

        self.assertEqual(format(email_last_sent_date, '.2f'),
                         format(constants.current_user['EmailLastSentDate'], '.2f'),
                         'Value should have been inserted into the database')

        # run again to see if update works correctly
        email_last_sent_date = time.time()
        queries.insert_email_last_sent_date(email_last_sent_date)

        self.assertEqual(format(email_last_sent_date, '.2f'),
                         format(constants.current_user['EmailLastSentDate'], '.2f'),
                         'Value should have been inserted into the database')


if __name__ == '__main__':
    unittest.main()
