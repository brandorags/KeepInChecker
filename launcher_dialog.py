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

# Form implementation generated from reading ui file 'gui/ui_files/launcher_dialog.ui'
#
# Created: Sun May 15 22:52:21 2016
#      by: pyside-uic 0.2.15 running on PySide 1.2.4
#
# WARNING! All changes made in this file will be lost!

import multiprocessing
import platform
import sys
import os

from PySide import QtCore, QtGui


class LauncherDialog(QtGui.QWidget):

    def __init__(self):
        super(LauncherDialog, self).__init__()
        self.setup_ui(self)

    def setup_ui(self, launcher_dialog):
        launcher_dialog.setObjectName('launcher_dialog')
        launcher_dialog.resize(321, 212)
        self.verticalLayoutWidget = QtGui.QWidget(launcher_dialog)
        self.verticalLayoutWidget.setGeometry(QtCore.QRect(0, 0, 321, 51))
        self.verticalLayoutWidget.setObjectName('verticalLayoutWidget')
        self.verticalLayout = QtGui.QVBoxLayout(self.verticalLayoutWidget)
        self.verticalLayout.setContentsMargins(0, 0, 0, 0)
        self.verticalLayout.setObjectName('verticalLayout')
        self.launcher_description_label = QtGui.QLabel(self.verticalLayoutWidget)
        self.launcher_description_label.setAlignment(QtCore.Qt.AlignCenter)
        self.launcher_description_label.setObjectName('launcher_description_label')
        self.verticalLayout.addWidget(self.launcher_description_label)
        self.horizontalLayoutWidget = QtGui.QWidget(launcher_dialog)
        self.horizontalLayoutWidget.setGeometry(QtCore.QRect(0, 50, 321, 51))
        self.horizontalLayoutWidget.setObjectName('horizontalLayoutWidget')
        self.horizontalLayout = QtGui.QHBoxLayout(self.horizontalLayoutWidget)
        self.horizontalLayout.setContentsMargins(0, 0, 0, 0)
        self.horizontalLayout.setObjectName('horizontalLayout')
        spacerItem = QtGui.QSpacerItem(23, 20, QtGui.QSizePolicy.Fixed, QtGui.QSizePolicy.Minimum)
        self.horizontalLayout.addItem(spacerItem)
        self.password_label = QtGui.QLabel(self.horizontalLayoutWidget)
        self.password_label.setObjectName('password_label')
        self.horizontalLayout.addWidget(self.password_label)
        spacerItem1 = QtGui.QSpacerItem(7, 20, QtGui.QSizePolicy.Fixed, QtGui.QSizePolicy.Minimum)
        self.horizontalLayout.addItem(spacerItem1)
        self.password_textbox = QtGui.QLineEdit(self.horizontalLayoutWidget)
        self.password_textbox.setObjectName('password_textbox')
        self.password_textbox.setEchoMode(QtGui.QLineEdit.Password)
        self.password_textbox.textChanged.connect(self.hide_incorrect_password_label)
        self.horizontalLayout.addWidget(self.password_textbox)
        spacerItem2 = QtGui.QSpacerItem(26, 20, QtGui.QSizePolicy.Fixed, QtGui.QSizePolicy.Minimum)
        self.horizontalLayout.addItem(spacerItem2)
        self.horizontalLayoutWidget_2 = QtGui.QWidget(launcher_dialog)
        self.horizontalLayoutWidget_2.setGeometry(QtCore.QRect(0, 100, 321, 71))
        self.horizontalLayoutWidget_2.setObjectName('horizontalLayoutWidget_2')
        self.horizontalLayout_2 = QtGui.QHBoxLayout(self.horizontalLayoutWidget_2)
        self.horizontalLayout_2.setContentsMargins(0, 0, 0, 0)
        self.horizontalLayout_2.setObjectName('horizontalLayout_2')
        self.ok_button = QtGui.QPushButton(self.horizontalLayoutWidget_2)
        palette = QtGui.QPalette()
        brush = QtGui.QBrush(QtGui.QColor(0, 0, 0))
        brush.setStyle(QtCore.Qt.SolidPattern)
        palette.setBrush(QtGui.QPalette.Active, QtGui.QPalette.ButtonText, brush)
        brush = QtGui.QBrush(QtGui.QColor(0, 0, 0))
        brush.setStyle(QtCore.Qt.SolidPattern)
        palette.setBrush(QtGui.QPalette.Inactive, QtGui.QPalette.ButtonText, brush)
        brush = QtGui.QBrush(QtGui.QColor(106, 104, 100))
        brush.setStyle(QtCore.Qt.SolidPattern)
        palette.setBrush(QtGui.QPalette.Disabled, QtGui.QPalette.ButtonText, brush)
        self.ok_button.setPalette(palette)
        self.ok_button.setCursor(QtCore.Qt.PointingHandCursor)
        self.ok_button.setObjectName('ok_button')
        self.ok_button.clicked.connect(self.launch_application)
        self.horizontalLayout_2.addWidget(self.ok_button)
        self.cancel_button = QtGui.QPushButton(self.horizontalLayoutWidget_2)
        self.cancel_button.setCursor(QtCore.Qt.PointingHandCursor)
        self.cancel_button.setObjectName('cancel_button')
        self.cancel_button.clicked.connect(self.cancel)
        self.horizontalLayout_2.addWidget(self.cancel_button)
        self.horizontalLayoutWidget_3 = QtGui.QWidget(launcher_dialog)
        self.horizontalLayoutWidget_3.setGeometry(QtCore.QRect(0, 170, 321, 31))
        self.horizontalLayoutWidget_3.setObjectName('horizontalLayoutWidget_3')
        self.horizontalLayout_3 = QtGui.QHBoxLayout(self.horizontalLayoutWidget_3)
        self.horizontalLayout_3.setContentsMargins(0, 0, 0, 0)
        self.horizontalLayout_3.setObjectName('horizontalLayout_3')
        self.incorrect_password_label = QtGui.QLabel(self.horizontalLayoutWidget_3)
        self.incorrect_password_label.setEnabled(True)
        palette = QtGui.QPalette()
        brush = QtGui.QBrush(QtGui.QColor(255, 0, 128))
        brush.setStyle(QtCore.Qt.SolidPattern)
        palette.setBrush(QtGui.QPalette.Active, QtGui.QPalette.WindowText, brush)
        brush = QtGui.QBrush(QtGui.QColor(255, 0, 128))
        brush.setStyle(QtCore.Qt.SolidPattern)
        palette.setBrush(QtGui.QPalette.Inactive, QtGui.QPalette.WindowText, brush)
        brush = QtGui.QBrush(QtGui.QColor(69, 69, 69))
        brush.setStyle(QtCore.Qt.SolidPattern)
        palette.setBrush(QtGui.QPalette.Disabled, QtGui.QPalette.WindowText, brush)
        self.incorrect_password_label.setPalette(palette)
        self.incorrect_password_label.setAlignment(QtCore.Qt.AlignCenter)
        self.incorrect_password_label.setObjectName('incorrect_password_label')
        self.incorrect_password_label.hide()
        self.horizontalLayout_3.addWidget(self.incorrect_password_label)

        self.retranslate_ui(launcher_dialog)
        QtCore.QMetaObject.connectSlotsByName(launcher_dialog)

        self.password_textbox.returnPressed.connect(self.launch_application)

        self.move(QtGui.QApplication.desktop().screen().rect().center() - self.rect().center())

    def retranslate_ui(self, launcher_dialog):
        launcher_dialog.setWindowTitle(QtGui.QApplication.translate('launcher_dialog', 'KeepInChecker Launcher', None,
                                                                    QtGui.QApplication.UnicodeUTF8))
        self.launcher_description_label.setText(QtGui.QApplication.
                                                translate('launcher_dialog',
                                                          'KeepInChecker Requires Authentication to Run.', None,
                                                          QtGui.QApplication.UnicodeUTF8))
        self.password_label.setText(QtGui.QApplication.translate('launcher_dialog', 'Password:', None,
                                                                 QtGui.QApplication.UnicodeUTF8))
        self.ok_button.setText(QtGui.QApplication.translate('launcher_dialog', 'Ok', None,
                                                            QtGui.QApplication.UnicodeUTF8))
        self.cancel_button.setText(QtGui.QApplication.translate('launcher_dialog', 'Cancel', None,
                                                                QtGui.QApplication.UnicodeUTF8))
        self.incorrect_password_label.setText(QtGui.QApplication.translate('launcher_dialog', 'Incorrect password.',
                                                                           None, QtGui.QApplication.UnicodeUTF8))

    def launch_application(self):
        password = self.password_textbox.text()
        operating_system = platform.system()

        if 'darwin' in operating_system.lower():
            if not self._is_password_correct(password):
                self.incorrect_password_label.show()
                return

            # because of how the path is generated with .app files, we need to remove the directories
            # from the launcher's path to correctly form the path where the app resides
            # so, original path = /Users/User/Launcher.app/Contents/MacOS
            # and new_path = /Users/User
            path_to_app = self._format_path(os.path.realpath(os.path.dirname(sys.argv[0])), 3)

            cmd = 'echo "' + password + '" | sudo -S ' + path_to_app + '/KeepInChecker.app/Contents' \
                                                                       '/MacOS/KeepInChecker.app &'
            os.system(cmd)

            remove_command_process = multiprocessing.Process(target=self._remove_command_from_shell_history)
            remove_command_process.start()

        sys.exit()

    def cancel(self):
        sys.exit()

    def hide_incorrect_password_label(self):
        self.incorrect_password_label.hide()

    def _is_password_correct(self, password):
        output_code = os.system('echo ' + password + ' | sudo -S -k ls')
        if output_code != 0:
            return False

        return True

    def _format_path(self, path, number_of_dirs_to_remove_from_path):
        formatted_path = path.split('/')

        for i in xrange(0, number_of_dirs_to_remove_from_path):
            del formatted_path[len(formatted_path) - 1]

        return '/'.join(formatted_path)

    def _get_shell_type(self):
        return os.getenv('SHELL').split('/')[2]

    def _remove_command_from_shell_history(self):
        current_username = os.popen('whoami').read().strip()
        hist_file_path = '/Users/' + current_username + '/.' + self._get_shell_type() + '_history'
        hist_file = open(hist_file_path, 'r')

        lines = hist_file.readlines()
        hist_file.close()

        hist_file = open(hist_file_path, 'w')
        for i in xrange(0, len(lines)):
            if i != len(lines) - 1 or \
                    'echo' and 'sudo' not in lines[i].lower() or \
                    'keepinchecker' not in lines[i].lower():
                hist_file.write(lines[i])

        hist_file.truncate()
        hist_file.close()

if __name__ == '__main__':
    app = QtGui.QApplication(sys.argv)
    launcher_dialog = LauncherDialog()
    launcher_dialog.show()
    launcher_dialog.activateWindow()
    launcher_dialog.raise_()
    sys.exit(app.exec_())
