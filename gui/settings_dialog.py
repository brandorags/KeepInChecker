# -*- coding: utf-8 -*-

# Form implementation generated from reading ui file 'ui_files/settings_dialog.ui'
#
# Created: Mon Feb 22 23:53:47 2016
#      by: pyside-uic 0.2.15 running on PySide 1.2.2
#
# WARNING! All changes made in this file will be lost!

import multiprocessing
import re
import sys
import os.path
sys.path.append(os.path.join(os.path.dirname(__file__), '..'))

from utilities.security_utilities import decrypt
from PySide import QtCore, QtGui
from constants import constants
from database import queries


class SettingsDialog(QtGui.QWidget):
    """
    This class creates the dialog box that allows
    the user to input his or her personal info,
    such as their username, email, etc.
    """

    def __init__(self):
        """
        Initializes the class.

        :var self.email_regex - a regex used to determine if a
        string is in an email format or not
        """
        QtGui.QWidget.__init__(self)
        self.setup_ui(self)

        self.email_regex = re.compile('^[\w\.\+\-]+@[\w]+\.[a-z]{2,6}$')

    def setup_ui(self, settings_dialog):
        """
        Creates the dialog box.

        :param settings_dialog:
        :return:
        """
        settings_dialog.setObjectName('settings_dialog')
        settings_dialog.resize(408, 279)
        self.horizontalLayoutWidget = QtGui.QWidget(settings_dialog)
        self.horizontalLayoutWidget.setGeometry(QtCore.QRect(10, 10, 381, 31))
        self.horizontalLayoutWidget.setObjectName('horizontalLayoutWidget')
        self.horizontalLayout = QtGui.QHBoxLayout(self.horizontalLayoutWidget)
        self.horizontalLayout.setContentsMargins(0, 0, 0, 0)
        self.horizontalLayout.setObjectName('horizontalLayout')
        self.name_label = QtGui.QLabel(self.horizontalLayoutWidget)
        self.name_label.setObjectName('name_label')
        self.horizontalLayout.addWidget(self.name_label)
        spacerItem = QtGui.QSpacerItem(68, 20, QtGui.QSizePolicy.Fixed, QtGui.QSizePolicy.Minimum)
        self.horizontalLayout.addItem(spacerItem)
        self.name_textbox = QtGui.QLineEdit(self.horizontalLayoutWidget)
        self.name_textbox.setObjectName('name_textbox')
        self.horizontalLayout.addWidget(self.name_textbox)
        self.horizontalLayoutWidget_2 = QtGui.QWidget(settings_dialog)
        self.horizontalLayoutWidget_2.setGeometry(QtCore.QRect(10, 40, 381, 31))
        self.horizontalLayoutWidget_2.setObjectName('horizontalLayoutWidget_2')
        self.horizontalLayout_2 = QtGui.QHBoxLayout(self.horizontalLayoutWidget_2)
        self.horizontalLayout_2.setContentsMargins(0, 0, 0, 0)
        self.horizontalLayout_2.setObjectName('horizontalLayout_2')
        self.email_label = QtGui.QLabel(self.horizontalLayoutWidget_2)
        self.email_label.setObjectName('email_label')
        self.horizontalLayout_2.addWidget(self.email_label)
        spacerItem1 = QtGui.QSpacerItem(70, 20, QtGui.QSizePolicy.Fixed, QtGui.QSizePolicy.Minimum)
        self.horizontalLayout_2.addItem(spacerItem1)
        self.email_textbox = QtGui.QLineEdit(self.horizontalLayoutWidget_2)
        self.email_textbox.setObjectName('email_textbox')
        self.horizontalLayout_2.addWidget(self.email_textbox)
        self.horizontalLayoutWidget_3 = QtGui.QWidget(settings_dialog)
        self.horizontalLayoutWidget_3.setGeometry(QtCore.QRect(10, 70, 381, 31))
        self.horizontalLayoutWidget_3.setObjectName('horizontalLayoutWidget_3')
        self.horizontalLayout_3 = QtGui.QHBoxLayout(self.horizontalLayoutWidget_3)
        self.horizontalLayout_3.setContentsMargins(0, 0, 0, 0)
        self.horizontalLayout_3.setObjectName('horizontalLayout_3')
        self.password_label = QtGui.QLabel(self.horizontalLayoutWidget_3)
        self.password_label.setObjectName('password_label')
        self.horizontalLayout_3.addWidget(self.password_label)
        spacerItem2 = QtGui.QSpacerItem(47, 9, QtGui.QSizePolicy.Fixed, QtGui.QSizePolicy.Minimum)
        self.horizontalLayout_3.addItem(spacerItem2)
        self.password_textbox = QtGui.QLineEdit(self.horizontalLayoutWidget_3)
        self.password_textbox.setEchoMode(QtGui.QLineEdit.Password)
        self.password_textbox.setObjectName('password_textbox')
        self.horizontalLayout_3.addWidget(self.password_textbox)
        self.horizontalLayoutWidget_4 = QtGui.QWidget(settings_dialog)
        self.horizontalLayoutWidget_4.setGeometry(QtCore.QRect(10, 100, 381, 91))
        self.horizontalLayoutWidget_4.setObjectName('horizontalLayoutWidget_4')
        self.horizontalLayout_5 = QtGui.QHBoxLayout(self.horizontalLayoutWidget_4)
        self.horizontalLayout_5.setContentsMargins(0, 0, 0, 0)
        self.horizontalLayout_5.setObjectName('horizontalLayout_5')
        self.partner_email_label = QtGui.QLabel(self.horizontalLayoutWidget_4)
        self.partner_email_label.setObjectName('partner_email_label')
        self.horizontalLayout_5.addWidget(self.partner_email_label)
        spacerItem3 = QtGui.QSpacerItem(10, 9, QtGui.QSizePolicy.Fixed, QtGui.QSizePolicy.Minimum)
        self.horizontalLayout_5.addItem(spacerItem3)
        self.partner_emails_table = QtGui.QTableWidget(self.horizontalLayoutWidget_4)
        self.partner_emails_table.setShowGrid(False)
        self.partner_emails_table.setRowCount(3)
        self.partner_emails_table.setColumnCount(1)
        self.partner_emails_table.setObjectName('partner_emails_table')
        self.partner_emails_table.setColumnCount(1)
        self.partner_emails_table.setRowCount(10)
        item = QtGui.QTableWidgetItem()
        self.partner_emails_table.setHorizontalHeaderItem(0, item)
        self.partner_emails_table.horizontalHeader().setStretchLastSection(True)
        self.horizontalLayout_5.addWidget(self.partner_emails_table)
        self.horizontalLayoutWidget_6 = QtGui.QWidget(settings_dialog)
        self.horizontalLayoutWidget_6.setGeometry(QtCore.QRect(10, 220, 381, 51))
        self.horizontalLayoutWidget_6.setObjectName('horizontalLayoutWidget_6')
        self.horizontalLayout_7 = QtGui.QHBoxLayout(self.horizontalLayoutWidget_6)
        self.horizontalLayout_7.setContentsMargins(0, 0, 0, 0)
        self.horizontalLayout_7.setObjectName('horizontalLayout_7')
        self.save_button = QtGui.QPushButton(self.horizontalLayoutWidget_6)
        self.save_button.setCursor(QtCore.Qt.PointingHandCursor)
        self.save_button.setObjectName('save_button')
        self.save_button.clicked.connect(self.save_user_settings)
        self.horizontalLayout_7.addWidget(self.save_button)
        self.cancel_button = QtGui.QPushButton(self.horizontalLayoutWidget_6)
        self.cancel_button.setCursor(QtCore.Qt.PointingHandCursor)
        self.cancel_button.setObjectName('cancel_button')
        self.cancel_button.clicked.connect(self.close_window)
        self.horizontalLayout_7.addWidget(self.cancel_button)
        self.horizontalLayoutWidget_5 = QtGui.QWidget(settings_dialog)
        self.horizontalLayoutWidget_5.setGeometry(QtCore.QRect(10, 190, 223, 29))
        self.horizontalLayoutWidget_5.setObjectName('horizontalLayoutWidget_5')
        self.horizontalLayout_6 = QtGui.QHBoxLayout(self.horizontalLayoutWidget_5)
        self.horizontalLayout_6.setContentsMargins(0, 0, 0, 0)
        self.horizontalLayout_6.setObjectName('horizontalLayout_6')
        self.email_frequency_label = QtGui.QLabel(self.horizontalLayoutWidget_5)
        self.email_frequency_label.setObjectName('email_frequency_label')
        self.horizontalLayout_6.addWidget(self.email_frequency_label)
        spacerItem4 = QtGui.QSpacerItem(6, 20, QtGui.QSizePolicy.Fixed, QtGui.QSizePolicy.Minimum)
        self.horizontalLayout_6.addItem(spacerItem4)
        self.email_frequency_combo_box = QtGui.QComboBox(self.horizontalLayoutWidget_5)
        self.email_frequency_combo_box.setCursor(QtCore.Qt.PointingHandCursor)
        self.email_frequency_combo_box.setObjectName('email_frequency_combo_box')
        self.email_frequency_combo_box.addItem('')
        self.email_frequency_combo_box.addItem('')
        self.horizontalLayout_6.addWidget(self.email_frequency_combo_box)

        self.retranslate_ui(settings_dialog)
        QtCore.QMetaObject.connectSlotsByName(settings_dialog)
        settings_dialog.setTabOrder(self.name_textbox, self.email_textbox)
        settings_dialog.setTabOrder(self.email_textbox, self.password_textbox)
        settings_dialog.setTabOrder(self.password_textbox, self.partner_emails_table)
        settings_dialog.setTabOrder(self.partner_emails_table, self.email_frequency_combo_box)
        settings_dialog.setTabOrder(self.email_frequency_combo_box, self.save_button)
        settings_dialog.setTabOrder(self.save_button, self.cancel_button)

        self.name_textbox.returnPressed.connect(self.save_user_settings)
        self.email_textbox.returnPressed.connect(self.save_user_settings)
        self.password_textbox.returnPressed.connect(self.save_user_settings)

        self.move(QtGui.QApplication.desktop().screen().rect().center() - self.rect().center())

    def retranslate_ui(self, settings_dialog):
        """
        Additional dialog box setup.

        :param settings_dialog:
        :return:
        """
        settings_dialog.setWindowTitle(QtGui.QApplication.translate('settings_dialog', 'Settings', None,
                                                                    QtGui.QApplication.UnicodeUTF8))
        self.name_label.setText(QtGui.QApplication.translate('settings_dialog', 'Name:', None,
                                                             QtGui.QApplication.UnicodeUTF8))
        self.email_label.setText(QtGui.QApplication.translate('settings_dialog', 'Email:', None,
                                                              QtGui.QApplication.UnicodeUTF8))
        self.password_label.setText(QtGui.QApplication.translate('settings_dialog', 'Password:', None,
                                                                 QtGui.QApplication.UnicodeUTF8))
        self.partner_email_label.setText(QtGui.QApplication.translate('settings_dialog', 'Partners\' Emails:',
                                                                      None, QtGui.QApplication.UnicodeUTF8))
        self.partner_emails_table.horizontalHeaderItem(0).setText(QtGui.QApplication.translate('settings_dialog',
                                                                                              'Email Address(es)', None,
                                                                                               QtGui.QApplication.UnicodeUTF8))
        self.save_button.setText(QtGui.QApplication.translate('settings_dialog', 'Save', None,
                                                              QtGui.QApplication.UnicodeUTF8))
        self.cancel_button.setText(QtGui.QApplication.translate('settings_dialog', 'Cancel', None,
                                                                QtGui.QApplication.UnicodeUTF8))
        self.email_frequency_label.setText(QtGui.QApplication.translate('settings_dialog', 'Email Frequency:', None,
                                                                        QtGui.QApplication.UnicodeUTF8))
        self.email_frequency_combo_box.setItemText(0, QtGui.QApplication.translate('settings_dialog', 'Daily', None,
                                                                                   QtGui.QApplication.UnicodeUTF8))
        self.email_frequency_combo_box.setItemText(1, QtGui.QApplication.translate('settings_dialog', 'Weekly', None,
                                                                                   QtGui.QApplication.UnicodeUTF8))

    def save_user_settings(self):
        """
        The action performed after the user presses the
        "Save" button. All the credentials the user has
        input into the dialog box will be saved to the
        database upon depressing this button.

        :return:
        """
        self.save_button.setDisabled(True)

        try:
            user_name = self.name_textbox.text()
            user_email = self.email_textbox.text()
            password = self.password_textbox.text()
            partner_emails_table = self.partner_emails_table
            email_frequency = self.email_frequency_combo_box.currentText()

            if self.are_fields_valid(user_name, user_email, password, partner_emails_table):
                partner_emails = self.partner_emails_to_comma_separated_list(self, partner_emails_table)

                save_user_process = multiprocessing.Process(target=queries.save_user_data,
                                                            args=(user_name, user_email, password,
                                                                  partner_emails, email_frequency))
                save_user_process.start()

                self.close_window()
                tray = QtGui.QSystemTrayIcon()
                tray.showMessage('Success', 'Your credentials have been saved')
        except:
            self.save_button.setDisabled(False)
            raise

        self.save_button.setDisabled(False)

    def are_fields_valid(self, user_name_text, user_email_text, password_text, partner_emails_table):
        """
        Checks to see if the fields the user has entered are valid
        (e.g., is the field empty? Is the email address in an
        actual email format?).

        :param user_name_text: - the text from the "Name" textbox
        :param user_email_text: - the text from the "Email" textbox
        :param password_text: - the text from the "Password" textbox
        :param partner_emails_table: - the table object which contains
        the user's accountability partners' email addresses
        :return: True if all of the fields meet the valid criteria;
        False if one to n number of the fields do not
        """
        valid_fields = True

        # validate user name
        if not user_name_text:
            self.name_textbox.setStyleSheet(self.get_error_background_color(self))
            valid_fields = False
        else:
            self.name_textbox.setStyleSheet(self.get_successful_background_color(self))

        # validate email address
        validated_email_textbox = self.email_regex.match(user_email_text)
        if not validated_email_textbox:
            self.email_textbox.setStyleSheet(self.get_error_background_color(self))
            valid_fields = False
        else:
            self.email_textbox.setStyleSheet(self.get_successful_background_color(self))

        # validate password
        if not password_text:
            self.password_textbox.setStyleSheet(self.get_error_background_color(self))
            valid_fields = False
        else:
            self.password_textbox.setStyleSheet(self.get_successful_background_color(self))

        # validate partner emails
        column_index = 0
        is_table_empty = True
        for row in xrange(partner_emails_table.rowCount()):
            email = partner_emails_table.item(row, column_index)
            if not email or not email.text():
                continue

            is_table_empty = False
            validated_email_text = self.email_regex.match(str(email.text()))
            if not validated_email_text:
                email.setBackground(QtGui.QBrush(QtGui.QColor(255, 186, 186)))
                valid_fields = False
            else:
                email.setBackground(QtGui.QBrush(QtGui.QColor(255, 255, 255)))

        if is_table_empty:
            partner_emails_table.setItem(0, 0, QtGui.QTableWidgetItem(''))
            partner_emails_table.itemAt(0, 0).setBackground(QtGui.QBrush(QtGui.QColor(255, 186, 186)))
            valid_fields = False

        return valid_fields

    def scroll_partner_emails_table_to_top(self):
        """
        Scrolls the partner emails table to the top
        where the first row becomes visible again.

        :return:
        """
        self.partner_emails_table.setItem(0, 0, QtGui.QTableWidgetItem(''))
        first_item = self.partner_emails_table.item(0, 0)
        self.partner_emails_table.scrollToItem(first_item, QtGui.QAbstractItemView.PositionAtTop)

    def set_user_settings_on_open(self):
        """
        Sets the user's credentials within the
        dialog box's fields upon the opening
        of the dialog itself.

        :return:
        """
        if constants.current_user:
            user = constants.current_user
        else:
            user = queries.get_current_user()

        if not user:
            return

        self.name_textbox.setText(decrypt(user['UserName']))
        self.email_textbox.setText(decrypt(user['UserEmail']))
        # the UserEmailPassword value needs to be doubly decrypted;
        # as to why, I'm not sure yet
        self.password_textbox.setText(decrypt(decrypt(user['UserEmailPassword'])))

        partner_emails = decrypt(user['PartnerEmails']).split(', ')
        row_index = 0
        for partner_email in partner_emails:
            self.partner_emails_table.setItem(row_index, 0, QtGui.QTableWidgetItem(partner_email))
            row_index += 1

    def close_window(self):
        """
        Closes the dialog box and sets the textbox fields
        to a clean slate to correctly populate the right
        data upon the reopening of the dialog box.

        :return:
        """
        self.name_textbox.setText('')
        self.name_textbox.setStyleSheet(self.get_successful_background_color(self))
        self.email_textbox.setText('')
        self.email_textbox.setStyleSheet(self.get_successful_background_color(self))
        self.password_textbox.setText('')
        self.password_textbox.setStyleSheet(self.get_successful_background_color(self))
        self.partner_emails_table.clearContents()

        self.name_textbox.setFocus()

        self.close()

    @staticmethod
    def get_error_background_color(self):
        """
        Gets the background color used to display
        a text field that was deemed invalid.

        :param self:
        :return: the background color for the textbox
        """
        return 'background-color: rgb(255, 186, 186)'

    @staticmethod
    def get_successful_background_color(self):
        """
        Gets the background color used to display
        a text field that was deemed valid.

        :param self:
        :return: the background color for the textbox
        """
        return 'background-color: rgb(255, 255, 255)'

    @staticmethod
    def partner_emails_to_comma_separated_list(self, partner_emails):
        """
        Gathers all of the user's accountability partners'
        email addresses and joins them in a csv format.

        Example: a@example.com,b@example.com,c@example.comd

        :param self:
        :param partner_emails: table object that contains
        the emails
        :return: a string of emails joined by commas
        """
        partner_emails_as_comma_sep_list = ''

        column_index = 0
        for row in xrange(partner_emails.rowCount()):
            partner_email = partner_emails.item(row, column_index)
            if not partner_email:
                continue

            if partner_emails_as_comma_sep_list != '':
                partner_emails_as_comma_sep_list += ', ' + str(partner_email.text())
            else:
                partner_emails_as_comma_sep_list = str(partner_email.text())

        return partner_emails_as_comma_sep_list.rstrip(', ')
