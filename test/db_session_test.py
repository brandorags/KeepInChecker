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
