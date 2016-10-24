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


import multiprocessing
import sys
import os
sys.path.append(os.path.join(os.path.dirname(__file__), '..'))

import keep_in_checker
import resources_rc

from gui.settings_dialog import SettingsDialog
from PySide.QtGui import *


class SystemTray(object):
    """
    This class creates the ability to use
    the system tray for different OSes.
    """

    def __init__(self):
        """
        Initializes the system tray icon and also
        sets up the selections that allows the user
        to input their personal info and to quit
        the application itself.
        """
        self.app = QApplication(sys.argv)

        menu = QMenu()
        settings_action = menu.addAction('Settings...')
        settings_action.triggered.connect(self.settings)
        quit_action = menu.addAction('Quit KeepInChecker')
        quit_action.triggered.connect(self.quit)

        icon = QIcon(':/gui/images/chevron-up.png')
        self.tray = QSystemTrayIcon()
        self.tray.setIcon(icon)
        self.tray.setContextMenu(menu)
        self.tray.setToolTip('KeepInChecker')
        self.tray.show()

        self.settings_dialog = SettingsDialog()

    def run(self):
        """
        Makes the call to start self.app, which
        when run, creates the system tray object.
        Simply put, calling this method runs the
        application.

        :return:
        """
        self.app.exec_()
        sys.exit()

    def settings(self):
        """
        Displays the settings dialog box.

        :return:
        """
        self.settings_dialog.scroll_partner_emails_table_to_top()
        self.settings_dialog.set_user_settings_on_open()
        self.settings_dialog.show()
        self.settings_dialog.activateWindow()
        self.settings_dialog.raise_()

    def quit(self):
        """
        Terminates the application entirely
        (even threads).

        :return:
        """
        keep_in_checker_backend.terminate()
        sys.exit()


if __name__ == '__main__':
    # begins the process that runs the background work,
    # such as packet sniffing, emailing, etc.
    keep_in_checker_backend = multiprocessing.Process(target=keep_in_checker.main)
    keep_in_checker_backend.start()

    system_tray_gui = SystemTray()
    system_tray_gui.app.setQuitOnLastWindowClosed(False)
    system_tray_gui.run()
