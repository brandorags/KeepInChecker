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
