import unittest
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


if __name__ == '__main__':
    unittest.main()
