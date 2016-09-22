# list that contains words/phrases that are considered objectionable content
objectionable_words_list = ['stackoverflow', 'imgur', 'reddit', 'awesome', 'the']

# list that contains websited that don't need to be sniffed
whitelisted_websites = []

# list that's used to parse through relevant packet data
packet_keywords = ['GET', 'Host', 'Referer']

# the cached user object that contains the user's current credentials
current_user = None

# the path to the database; this frees up searching for the filename
# every time a database transaction is made
database_path = None
