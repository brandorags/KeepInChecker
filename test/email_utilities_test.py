import unittest

from utilities import email_utilities
from constants import constants
from datetime import datetime


class EmailUtilitiesTest(unittest.TestCase):

    def test_has_email_been_sent(self):
        user = {'EmailFrequency': 'Daily'}
        constants.current_user = user

        # check if email has been sent with the current time
        # as the time representing when the email was sent
        self.assertTrue(email_utilities.has_email_been_sent(), 'Email should have been sent already')

        # check if email has been sent with the current time minus
        # a day as the time representing when the email was sent
        now = datetime.now()
        then = datetime(now.year, now.month, (now.day - 1))
        email_utilities.date_last_email_was_sent = then
        self.assertFalse(email_utilities.has_email_been_sent(), 'Email should not have been sent yet')

    def test_get_mail_server(self):
        gmail_address = 'test@gmail.com'
        self.assertEqual(email_utilities.get_mail_server(gmail_address), 'smtp.gmail.com',
                         'Strings should be equal')

        yahoo_address = 'test@yahoo.com'
        self.assertEqual(email_utilities.get_mail_server(yahoo_address), 'smtp.mail.yahoo.com',
                         'Strings should be equal')

        outlook_address = 'test@outlook.com'
        self.assertEqual(email_utilities.get_mail_server(outlook_address), 'smtp-mail.outlook.com',
                         'Strings should be equal')

    def test_get_port(self):
        gmail_mail_server = 'smtp.gmail.com'
        self.assertEqual(email_utilities.get_port(gmail_mail_server), 587, 'Integers should be equal')

        verizon_mail_server = 'smtp.verizon.net'
        self.assertEqual(email_utilities.get_port(verizon_mail_server), 465, 'Integers should be equal')


if __name__ == '__main__':
    unittest.main()
