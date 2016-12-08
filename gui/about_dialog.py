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


# -*- coding: utf-8 -*-

# Form implementation generated from reading ui file 'gui/ui_files/about_dialog.ui'
#
# Created: Mon Dec  5 22:43:34 2016
#      by: pyside-uic 0.2.15 running on PySide 1.2.4
#
# WARNING! All changes made in this file will be lost!

from constants.constants import third_party_library_dict
from PySide import QtCore, QtGui


class AboutDialog(QtGui.QWidget):
    """
    This class creates the dialog box that displays
    information about the application.
    """

    def __init__(self):
        """
        Initializes the class.
        """
        QtGui.QWidget.__init__(self)
        self.setup_ui(self)

    def setup_ui(self, about_dialog):
        """
        Creates the dialog box.

        :param about_dialog:
        :return:
        """
        about_dialog.setObjectName('about_dialog')
        about_dialog.resize(401, 323)
        self.image_label = QtGui.QLabel(about_dialog)
        self.image_label.setGeometry(QtCore.QRect(30, 10, 64, 64))
        self.image_label.setText('')
        self.image_label.setObjectName('image_label')
        logo_image = QtGui.QPixmap(':/gui/images/chevron-up.png')
        self.image_label.setPixmap(logo_image)
        self.image_label.setScaledContents(True)
        self.title_label = QtGui.QLabel(about_dialog)
        self.title_label.setGeometry(QtCore.QRect(100, 10, 261, 71))
        font = QtGui.QFont()
        font.setPointSize(24)
        font.setWeight(50)
        font.setBold(False)
        self.title_label.setFont(font)
        self.title_label.setAlignment(QtCore.Qt.AlignCenter)
        self.title_label.setObjectName('title_label')
        self.verticalLayoutWidget = QtGui.QWidget(about_dialog)
        self.verticalLayoutWidget.setGeometry(QtCore.QRect(20, 90, 361, 51))
        self.verticalLayoutWidget.setObjectName('verticalLayoutWidget')
        self.gpl_vertical_layout = QtGui.QVBoxLayout(self.verticalLayoutWidget)
        self.gpl_vertical_layout.setContentsMargins(0, 0, 0, 0)
        self.gpl_vertical_layout.setObjectName('gpl_vertical_layout')
        self.gpl_label = QtGui.QLabel(self.verticalLayoutWidget)
        self.gpl_label.setAlignment(QtCore.Qt.AlignCenter)
        self.gpl_label.setWordWrap(True)
        self.gpl_label.setObjectName('gpl_label')
        self.gpl_vertical_layout.addWidget(self.gpl_label)
        self.verticalLayoutWidget_2 = QtGui.QWidget(about_dialog)
        self.verticalLayoutWidget_2.setGeometry(QtCore.QRect(20, 180, 361, 80))
        self.verticalLayoutWidget_2.setObjectName('verticalLayoutWidget_2')
        self.libraries_vertical_layout = QtGui.QVBoxLayout(self.verticalLayoutWidget_2)
        self.libraries_vertical_layout.setContentsMargins(0, 0, 0, 0)
        self.libraries_vertical_layout.setObjectName('libraries_vertical_layout')
        self.libraries_text_browser = QtGui.QTextBrowser(self.verticalLayoutWidget_2)
        self.libraries_text_browser.setObjectName('libraries_text_browser')
        self.libraries_vertical_layout.addWidget(self.libraries_text_browser)
        self.libraries_label = QtGui.QLabel(about_dialog)
        self.libraries_label.setGeometry(QtCore.QRect(20, 150, 121, 21))
        self.libraries_label.setObjectName('libraries_label')
        self.verticalLayoutWidget_3 = QtGui.QWidget(about_dialog)
        self.verticalLayoutWidget_3.setGeometry(QtCore.QRect(20, 270, 361, 51))
        self.verticalLayoutWidget_3.setObjectName('verticalLayoutWidget_3')
        self.gpl_vertical_layout_2 = QtGui.QVBoxLayout(self.verticalLayoutWidget_3)
        self.gpl_vertical_layout_2.setContentsMargins(0, 0, 0, 0)
        self.gpl_vertical_layout_2.setObjectName('gpl_vertical_layout_2')
        self.gpl_label_2 = QtGui.QLabel(self.verticalLayoutWidget_3)
        self.gpl_label_2.setAlignment(QtCore.Qt.AlignCenter)
        self.gpl_label_2.setWordWrap(True)
        self.gpl_label_2.setObjectName('gpl_label_2')
        self.gpl_vertical_layout_2.addWidget(self.gpl_label_2)

        self.retranslate_ui(about_dialog)
        QtCore.QMetaObject.connectSlotsByName(about_dialog)

        # center the dialog
        self.move(QtGui.QApplication.desktop().screen().rect().center() - self.rect().center())

    def retranslate_ui(self, about_dialog):
        about_dialog.setWindowTitle(QtGui.QApplication.translate('about_dialog', 'About KeepInChecker', None,
                                                                 QtGui.QApplication.UnicodeUTF8))
        self.title_label.setText(QtGui.QApplication.translate('about_dialog', 'KeepInChecker', None,
                                                              QtGui.QApplication.UnicodeUTF8))
        self.gpl_label.setText(QtGui.QApplication.translate('about_dialog', 'KeepInChecker is distributed under the'
                                                                            ' terms of the GNU General Public License'
                                                                            ' (GPL) version 3.', None,
                                                            QtGui.QApplication.UnicodeUTF8))
        self.libraries_label.setText(QtGui.QApplication.translate('about_dialog', 'This software uses:', None,
                                                                  QtGui.QApplication.UnicodeUTF8))
        self.gpl_label_2.setText(QtGui.QApplication.translate('about_dialog', 'Copyright (c) Brandon Ragsdale 2016',
                                                              None, QtGui.QApplication.UnicodeUTF8))
        self.display_libraries()

    def display_libraries(self):
        """
        Displays the list of third party libraries
        used within the application.

        :return:
        """
        libraries_as_list = ''
        for key, value in sorted(third_party_library_dict.iteritems()):
            libraries_as_list += key + ': ' + value + '<br /> -- <br />'

        self.libraries_text_browser.setHtml('<html><body>' + libraries_as_list + '</body></html>')
