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


def convert_output_to_string(output_object):
    """
    Returns a string representation of standard output from the command line. In order
    to remove the newline character, splitting it from the rest of the output is mandatory.

    :param output_object: a subprocess.Popopen object that contains the command output
    """
    return output_object.stdout.read().decode('utf-8').split('\n')

