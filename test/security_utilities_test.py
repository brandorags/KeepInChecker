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

from utilities.security_utilities import encrypt, decrypt


url = 'http://example.com/example/123456/page?test=14'
name = 'John Doe'
various_characters = '1234567890!@#$%^&*()_+abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ{}:"<>?[];\',./'
generated_password = '#W,8R}GJS.\'+XEpkZ{4wX#:%$zN"Rssp*)\4s)dV'


class SecurityUtilitiesTest(unittest.TestCase):

    def test_encyrpt(self):
        encrypted_url = encrypt(url)
        self.assertNotEqual(encrypted_url, url, 'Strings should not be the same')

        encrypted_name = encrypt(name)
        self.assertNotEqual(encrypted_name, name, 'Strings should not be the same')

        encrypted_various_characters = encrypt(various_characters)
        self.assertNotEqual(encrypted_various_characters, various_characters, 'Strings should not be the same')

    def test_decrypt(self):
        encrypted_url = encrypt(url)
        decrypted_url = decrypt(encrypted_url)
        self.assertEqual(decrypted_url, url, 'Strings should be the same')

        encrypted_name = encrypt(name)
        decrypted_name = decrypt(encrypted_name)
        self.assertEqual(decrypted_name, name, 'Strings should be the same')

        encrypted_various_characters = encrypt(various_characters)
        decrypted_various_characters = decrypt(encrypted_various_characters)
        self.assertEqual(decrypted_various_characters, various_characters, 'Strings should be the same')

        encrypted_generated_password = encrypt(generated_password)
        decrypted_generated_password = decrypt(encrypted_generated_password)
        self.assertEqual(decrypted_generated_password, generated_password, 'Strings should be the same')


if __name__ == '__main__':
    unittest.main()
