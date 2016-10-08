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
