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


import platform
import os


# list that contains words/phrases that are considered objectionable content
objectionable_words_list = ['stackoverflow', 'imgur', 'reddit', 'awesome', 'the']

# list that contains websited that don't need to be sniffed
whitelisted_websites = []

# list that's used to parse through relevant packet data
packet_keywords = ['GET', 'Host', 'Referer']

# the cached user object that contains the user's current credentials
current_user = None

# the dictionary of libraries that the app currently uses
third_party_library_dict = {'altgraph (0.12)': 'https://pypi.python.org/pypi/altgraph/',
                            'impacket (0.9.15)': 'https://pypi.python.org/pypi/impacket/0.9.15',
                            'macholib (1.7)': 'https://pypi.python.org/pypi/macholib/',
                            'modulegraph (0.12.1)': 'https://pypi.python.org/pypi/modulegraph/',
                            'netifaces (0.10.5)': 'https://pypi.python.org/pypi/netifaces',
                            'pyaes (1.6.0)': 'https://pypi.python.org/pypi/pyaes',
                            'pcapy (0.10.10)': 'https://pypi.python.org/pypi/pcapy',
                            'py2app (0.10)': 'https://pypi.python.org/pypi/py2app/',
                            'PyInstaller (3.2)': 'http://www.pyinstaller.org/',
                            'PySide (1.2.4)': 'https://wiki.qt.io/PySide',
                            'Qt (4.8.7_2)': 'https://www.qt.io/'}

# the path to the database; this frees up processing time for when
# searching for the filename every time a database transaction is
# made; a path to the database will be created if the database
# doesn't exist
operating_system = platform.system()
database_path = ''
if 'darwin' or 'linux' in operating_system.lower():
    database_path = '/usr/local/.KeepInChecker/KeepInChecker.sqlite'
    if not os.path.exists(database_path):
        os.system('mkdir /usr/local/.KeepInChecker')
elif 'windows' in operating_system.lower():
    database_path = 'C:\\KeepInChecker\\KeepInChecker.sqlite'
    if not os.path.exists(database_path):
        os.system('mkdir C:\\KeepInChecker')

database_path = database_path
