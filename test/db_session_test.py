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


import unittest

from database.db_session import DbSession


class DbSessionTest(unittest.TestCase):

    def test_create_tables_if_none_exist(self):
        db = DbSession(':memory:')
        db.create_tables_if_none_exist()

        db.cursor.execute('SELECT name FROM sqlite_master WHERE type="table"')

        expected_tables = ['User', 'Packet']
        actual_tables = []
        for table in db.cursor.fetchall():
            actual_tables.append(str(table[0]))

        db.commit_and_close()

        self.assertItemsEqual(expected_tables, actual_tables, 'Should have the same tables')

    def test_commit(self):
        db = DbSession(':memory:')
        db.cursor.execute('CREATE TABLE Test(Id INT)')
        db.cursor.execute('INSERT INTO Test VALUES(1)')
        db.commit()

        db.cursor.execute('SELECT * FROM Test')
        db.commit()

        value = db.cursor.fetchone()[0]

        db.close()

        self.assertEqual(1, value, 'Value should contain the number 1')


if __name__ == '__main__':
    unittest.main()
