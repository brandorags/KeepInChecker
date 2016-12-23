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

import datetime as dt
import time as t
import smtplib

from email.mime.multipart import MIMEMultipart
from email.mime.text import MIMEText
from constants import constants
from database import queries


# keeps track of the date in which
# the last email was sent
date_last_email_was_sent = queries.get_email_last_sent_date()

# the number of days to wait to send
# the next email when the email
# frequency is on a daily basis
daily_frequency = 1

# the number of days to wait to send
# the next email when the email
# frequency is on a weekly basis
weekly_frequency = 7


def send_scheduled_email():
    """
    Sends a scheduled email based upon the frequency the user
    has set for when the activity report is to be emailed to
    the user's accountability partners.

    :return:
    """
    global date_last_email_was_sent

    if not date_last_email_was_sent:
        set_date_last_email_was_sent()

    if has_scheduled_email_been_sent():
        return

    sender_name = constants.current_user['UserName']
    sender_email = constants.current_user['UserEmail']
    try:
        sender_password = constants.current_user['UserEmailPassword']
    except:
        sender_password = constants.current_user['UserEmailPassword']
    recipients = constants.current_user['PartnerEmails']

    message = MIMEMultipart()
    message['From'] = sender_email
    message['Subject'] = 'KeepInChecker User Activity Report for ' + sender_name
    message.attach(MIMEText(generate_body_text(sender_name), 'plain'))

    recipients_list = [recipient.strip(' ') for recipient in recipients.split(',')]
    mail_server = get_mail_server(sender_email)
    port = get_port(mail_server)

    server = smtplib.SMTP(mail_server, port)
    server.starttls()
    server.login(sender_email, sender_password)
    text = message.as_string()
    server.sendmail(sender_email, recipients_list, text)
    server.quit()

    set_date_last_email_was_sent()


def set_date_last_email_was_sent():
    global date_last_email_was_sent

    date_last_email_was_sent = t.time()
    queries.save_email_last_sent_date(date_last_email_was_sent)


def has_scheduled_email_been_sent():
    """
    Checks the last time an email was sent, and
    with that timestamp verifies whether the next
    email needs to be sent or not.

    :return:
    """
    global date_last_email_was_sent
    global daily_frequency
    global weekly_frequency

    if constants.current_user['EmailFrequency'] == 'Daily':
        email_frequency = daily_frequency
    else:
        email_frequency = weekly_frequency

    now = dt.datetime.now()
    last = dt.datetime.fromtimestamp(date_last_email_was_sent)
    time_between_last_email_sent_to_now = now - last

    # if the time in days of the last email being sent
    # is greater than or equal to the email frequency, then the email
    # hasn't been sent, and thus we need to send it
    if time_between_last_email_sent_to_now.days >= email_frequency:
        return False

    return True


def generate_body_text(sender_name):
    """
    Creates the body of the activity
    report. This will display all
    objectionable content found.

    :param sender_name: the name of the user (e.g., "John Doe")
    :return:
    """
    email_body = 'Hello,\n\nOn behalf of ' + sender_name + ', you have received this email' \
                                                           ' with the following data,\n\n'

    packets = get_packet_data_for_email()
    if packets:
        packet_data = ''
        for packet in packets:
            packet_data += '\n' + packet['DateReceived'] + ' ' + packet['Timezone'] + ' ' + \
                           packet['Get'] + ' ' + packet['Host'] + ' ' + packet['Referer']

        email_body += str(len(packets)) + ' questionable sites were visited:\n'
        email_body += packet_data
    else:
        email_body += '0 questionable sites were visited.'

    email_body += '\n\nKeep staying accountable!\n\nSincerely,\nKeepInChecker'

    return email_body


def get_packet_data_for_email():
    """
    Calls the query that retrieves
    all the packets in the database.

    :return: a list of all the
    packet objects from the database
    """
    return queries.get_packets()


def get_mail_server(sender_email):
    """
    Gets the URL of the mail server
    given an email address.

    :param sender_email: the email address
    of the sender
    :return: a URL associated with the
    sender's email address domain (e.g.
    example@gmail.com would return
    'smtp.gmail.com'); an empty string
    if the domain is unknown
    """
    google_domains = ['gmail', 'googlemail']
    yahoo_domains = ['yahoo', 'ymail']
    microsoft_domains = ['live', 'msn', 'hotmail', 'outlook', 'passport']
    aol_domains = ['aol', 'aim']
    comcast_domains = ['comcast']
    verizon_domains = ['verizon']
    att_domains = ['att']

    sender_email = sender_email.lower()

    if any(domain in sender_email for domain in google_domains):
        return 'smtp.gmail.com'
    elif any(domain in sender_email for domain in yahoo_domains):
        return 'smtp.mail.yahoo.com'
    elif any(domain in sender_email for domain in microsoft_domains):
        return 'smtp-mail.outlook.com'
    elif any(domain in sender_email for domain in aol_domains):
        return 'smtp.aol.com'
    elif any(domain in sender_email for domain in comcast_domains):
        return 'smtp.comcast.net'
    elif any(domain in sender_email for domain in verizon_domains):
        return 'smtp.verizon.net'
    elif any(domain in sender_email for domain in att_domains):
        return 'smtp.att.net'

    return ''


def get_port(mail_server):
    """
    Gets the port number of a given
    email server.

    :param mail_server: the URL of the
    mail server
    :return: a number that corresponds
    to the mail server (e.g. 'smtp.gmail.com'
    would return the number 587); 0 if the
    mail server is unknown
    """
    five_eighty_seven_port_domains = ['smtp.gmail.com', 'smtp.mail.yahoo.com', 'smtp-mail.outlook.com',
                                      'smtp.aol.com', 'smtp.comcast.net']
    four_sixty_five_port_domains = ['smtp.verizon.net', 'smtp.att.net']

    if any(domain in mail_server for domain in five_eighty_seven_port_domains):
        return 587
    elif any(domain in mail_server for domain in four_sixty_five_port_domains):
        return 465

    return 0
