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
