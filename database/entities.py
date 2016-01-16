from pony.orm import *


db = Database('sqlite', 'KeepInChecker.sqlite', create_db=False)


class Packets(db.Entity):
    PacketId = PrimaryKey(int)
    Date_Recieved = Required(str)
    Timezone = Required(str)
    Get = Optional(str)
    Host = Optional(str)
    Referer = Optional(str)


db.generate_mapping(check_tables=True, create_tables=True)