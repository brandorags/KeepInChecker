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


from pysqlcipher import dbapi2 as sqlite3


class DbSession(object):
    """
    This class provides a layer of abstraction for
    when performing database transactions.
    """

    def __init__(self, database_path):
        """
        Makes the connection to the database, which allows for
        transactions to take place.
        """
        pragma = 'thisIsThePragma5345d5918Jn'  # this key is NOT used in production

        self.database_path = database_path

        self.connection = sqlite3.connect(self.database_path)
        self.connection.text_factory = str
        self.connection.row_factory = sqlite3.Row

        self.cursor = self.connection.cursor()
        self.cursor.execute('PRAGMA key=' + pragma)

    def get_database_path(self):
        """
        Getter for database_path

        :return: the path to the database
        """
        return self.database_path

    def set_database_path(self, path):
        """
        Setter for database_path

        :param path: the path to the database
        """
        self.database_path = path

    def commit(self):
        """
        Commits the database transaction. close(self) must be called after
        this method to successfully disconnect from the database.
        """
        self.connection.commit()

    def close(self):
        """
        Terminates the connection to the database.
        """
        self.cursor.close()
        self.connection.close()

    def commit_and_close(self):
        """
        Commits and closes the transaction.
        """
        self.connection.commit()
        self.cursor.close()
        self.connection.close()

    def create_tables_if_none_exist(self):
        """
        Creates the tables Packet and User if they don't already exist.
        """
        self.cursor.execute('''CREATE TABLE IF NOT EXISTS "Packet" (
                                    `PacketId` INTEGER NOT NULL,
                                    `DateReceived` TEXT NOT NULL,
                                    `Timezone` TEXT,
                                    `Get` TEXT,
                                    `Host` TEXT,
                                    `Referer` TEXT,
                                    PRIMARY KEY(PacketId)
                                )''')

        self.cursor.execute('''CREATE TABLE IF NOT EXISTS "User" (
                                    `UserId` INTEGER NOT NULL,
                                    `UserName` TEXT NOT NULL,
                                    `UserEmail` TEXT NOT NULL,
                                    `UserEmailPassword` TEXT NOT NULL,
                                    `PartnerEmails` TEXT NOT NULL,
                                    `EmailFrequency` TEXT NOT NULL,
                                    `EmailLastSentDate` REAL,
                                    PRIMARY KEY(UserId)
                                )''')
