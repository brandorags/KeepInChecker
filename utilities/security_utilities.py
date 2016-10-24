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


import pyaes

from constants import secret_key


key = secret_key.key


def encrypt(value):
    """
    Encrypts the data.

    :param value: the data to encrypt
    :return: an encrypted value of
    the parameter passed in
    """
    aes = pyaes.AESModeOfOperationCTR(key)
    encrypted_value = aes.encrypt(value)

    return encrypted_value


def decrypt(value):
    """
    Decrypts the data.

    :param value: encrypted data
    :return: a decrypted value of
    the parameter passed in
    """
    aes = pyaes.AESModeOfOperationCTR(key)
    decrypted_value = aes.decrypt(value)

    return decrypted_value
