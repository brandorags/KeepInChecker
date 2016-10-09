from utilities.security_utilities import encrypt, decrypt
from database.db_session import DbSession
from constants import constants
from time import tzname


def save_user_data(user_name, user_email, user_email_password,
                   partner_emails, email_frequency):
    """
    Inserts a new user's personal data, such as the username,
     email address, email password, etc., into the database.

    :param user_name: the name of the user (e.g., "John Doe")
    :param user_email: the user's email address
    :param user_email_password: the password of the user's email address
    :param partner_emails: the email addresses of the user's accountability partners
    :param email_frequency: the frequency in which to send the emails (e.g., "Daily")
    :return:
    """
    current_user = get_current_user()
    if current_user:
        update_user_data(current_user, user_name, user_email,
                         user_email_password, partner_emails, email_frequency)
        return

    db = DbSession(constants.database_path)
    sql = 'INSERT INTO User (UserName, UserEmail, UserEmailPassword, PartnerEmails, EmailFrequency) ' \
          'VALUES(\'' + encrypt(user_name) + '\'' + ',\'' + encrypt(user_email) + '\'' + ',\'' + \
          encrypt(user_email_password) + '\'' + ',\'' + encrypt(partner_emails) + '\'' + ',\'' + \
          encrypt(email_frequency) + '\')'

    db.cursor.execute(sql)
    db.commit_and_close()

    constants.current_user = get_current_user()


def update_user_data(existing_user, user_name, user_email, user_email_password,
                     partner_emails, email_frequency):
    """
    Updates an existing user's personal data,such as the username,
     email address, email password, etc., into the database.

    :param existing_user: the object which contains all of the user's existing data
    :param user_name: the name of the user (e.g., "John Doe")
    :param user_email: the user's email address
    :param user_email_password: the password of the user's email address
    :param partner_emails: the email addresses of the user's accountability partners
    :param email_frequency: the frequency in which to send the emails (e.g., "Daily")
    :return:
    """
    db = DbSession(constants.database_path)
    sql = 'UPDATE User SET'
    update_values = ''
    needs_update = False

    if decrypt(existing_user['UserName']) != user_name:
        update_values += ' UserName = \'' + encrypt(user_name) + '\','
        needs_update = True

    if decrypt(existing_user['UserEmail']) != user_email:
        update_values += ' UserEmail = \'' + encrypt(user_email) + '\','
        needs_update = True

    if decrypt(existing_user['UserEmailPassword']) != user_email_password:
        update_values += ' UserEmailPassword = \'' + encrypt(user_email_password) + '\','
        needs_update = True

    if decrypt(existing_user['PartnerEmails']) != partner_emails:
        update_values += ' PartnerEmails = \'' + encrypt(partner_emails) + '\','
        needs_update = True

    if decrypt(existing_user['EmailFrequency']) != email_frequency:
        update_values += ' EmailFrequency = \'' + encrypt(email_frequency) + '\''
        needs_update = True

    if needs_update:
        update_values = update_values.strip(',')  # remove any trailing commas
        sql += update_values + ' WHERE UserId = 1'

        db.cursor.execute(sql)
        db.commit_and_close()

        constants.current_user = get_current_user()


def insert_packets(objective_packets):
    """
    Inserts the objectionable packets found via the packet sniffer.

    :param objective_packets: a list that contains all the
     packet objects which contain objectionable content
    :return:
    """
    db = DbSession(constants.database_path)

    for obj_packet in objective_packets:
        for timestamp in obj_packet:
            date_received_value = encrypt(timestamp)
            timezone_value = tzname[0]
            get_value = encrypt(obj_packet[timestamp][0])
            host_value = encrypt(obj_packet[timestamp][1])
            referer_value = encrypt(obj_packet[timestamp][2])

            sql = 'INSERT INTO Packet (DateReceived, Timezone, Get, Host, Referer)' \
                  ' VALUES(\'' + str(date_received_value) + '\'' + ',' + \
                  '\'' + timezone_value + '\',' + '\'' + get_value + '\',' + \
                  '\'' + host_value + '\',' + '\'' + referer_value + '\')'

            db.cursor.execute(sql)

    db.commit_and_close()


def get_packets():
    """
    Gets all of the packets that are stored in the database.

    :return: a list of packet objects
    """
    db = DbSession(constants.database_path)
    packets = db.cursor.execute('SELECT * FROM Packet').fetchall()

    db.close()

    return packets


def get_current_user():
    """
    Gets the personal data of the current user, such as the username,
     email address, email password, etc., from the database.

    :return: an object which contains the user's personal data
    """
    db = DbSession(constants.database_path)
    db.cursor.execute('SELECT * FROM User')
    current_user = db.cursor.fetchone()

    db.close()

    return current_user
