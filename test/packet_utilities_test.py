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

from utilities import packet_utilities

packet1 = 'Ether: e0:f8:47:01:fd:6e -> 90:ef:68:ac:98:78 \nIP DF 192.168.0.3 -> 104.16.36.249\nTCP ack push 56074 -> 80\n4745 5420 2f71 7565 7374 696f 6e73 2f38    GET /questions/8\n3932 3238 2f63 616c 6c69 6e67 2d61 6e2d    9228/calling-an-\n6578 7465 726e 616c 2d63 6f6d 6d61 6e64    external-command\n2d69 6e2d 7079 7468 6f6e 3f72 713d 3120    -in-python?rq=1 \n4854 5450 2f31 2e31 0d0a 486f 7374 3a20    HTTP/1.1..Host: \n7374 6163 6b6f 7665 7266 6c6f 772e 636f    stackoverflow.co\n6d0d 0a55 7365 722d 4167 656e 743a 204d    m..User-Agent: M\n6f7a 696c 6c61 2f35 2e30 2028 4d61 6369    ozilla/5.0 (Maci\n6e74 6f73 683b 2049 6e74 656c 204d 6163    ntosh; Intel Mac\n204f 5320 5820 3130 2e31 313b 2072 763a     OS X 10.11; rv:\n3434 2e30 2920 4765 636b 6f2f 3230 3130    44.0) Gecko/2010\n3031 3031 2046 6972 6566 6f78 2f34 342e    0101 Firefox/44.\n300d 0a41 6363 6570 743a 2074 6578 742f    0..Accept: text/\n6874 6d6c 2c61 7070 6c69 6361 7469 6f6e    html,application\n2f78 6874 6d6c 2b78 6d6c 2c61 7070 6c69    /xhtml+xml,appli\n6361 7469 6f6e 2f78 6d6c 3b71 3d30 2e39    cation/xml;q=0.9\n2c2a 2f2a 3b71 3d30 2e38 0d0a 4163 6365    ,*/*;q=0.8..Acce\n7074 2d4c 616e 6775 6167 653a 2065 6e2d    pt-Language: en-\n5553 2c65 6e3b 713d 302e 350d 0a41 6363    US,en;q=0.5..Acc\n6570 742d 456e 636f 6469 6e67 3a20 677a    ept-Encoding: gz\n6970 2c20 6465 666c 6174 650d 0a44 4e54    ip, deflate..DNT\n3a20 310d 0a52 6566 6572 6572 3a20 6874    : 1..Referer: ht\n7470 3a2f 2f73 7461 636b 6f76 6572 666c    tp://stackoverfl\n6f77 2e63 6f6d 2f71 7565 7374 696f 6e73    ow.com/questions\n2f33 3330 3432 3834 362f 656d 6169 6c2d    /33042846/email-\n6263 632d 7265 6369 7069 656e 7473 2d6e    bcc-recipients-n\n6f74 2d68 6964 6465 6e2d 7573 696e 672d    ot-hidden-using-\n7079 7468 6f6e 2d73 6d74 706c 6962 3f6c    python-smtplib?l\n713d 310d 0a43 6f6f 6b69 653a 205f 5f63    q=1..Cookie: __c\n6664 7569 643d 6439 3635 3031 3363 3561    fduid=d965013c5a\n6562 3965 6432 3432 6134 6662 3364 3964    eb9ed242a4fb3d9d\n3630 6438 3965 3831 3435 3739 3333 3332    60d89e8145793332\n383b 2070 726f 763d 3462 3161 6331 3436    8; prov=4b1ac146\n2d36 6361 322d 3463 3334 2d62 3563 662d    -6ca2-4c34-b5cf-\n3661 3333 3137 3530 6130 6361 0d0a 436f    6a331750a0ca..Co\n6e6e 6563 7469 6f6e 3a20 6b65 6570 2d61    nnection: keep-a\n6c69 7665 0d0a 0d0a                        live....'
packet2 = 'Ether: e0:f8:47:01:fd:6e -> 90:ef:68:ac:98:78\nIP DF 192.168.0.3 -> 104.16.33.249 \nTCP ack push 58256 -> 80\n\n4745 5420 2f74 6f70 6261 722f 7369 7465    GET /topbar/site\n2d73 7769 7463 6865 722f 7369 7465 2d6c    -switcher/site-l\n6973 743f 5f3d 3134 3538 3130 3639 3038    ist?_=1458106908\n3932 3420 4854 5450 2f31 2e31 0d0a 486f    924 HTTP/1.1..Ho\n7374 3a20 7374 6163 6b6f 7665 7266 6c6f    st: stackoverflo\n772e 636f 6d0d 0a55 7365 722d 4167 656e    w.com..User-Agen\n743a 204d 6f7a 696c 6c61 2f35 2e30 2028    t: Mozilla/5.0 (\n4d61 6369 6e74 6f73 683b 2049 6e74 656c    Macintosh; Intel\n204d 6163 204f 5320 5820 3130 2e31 313b     Mac OS X 10.11;\n2072 763a 3434 2e30 2920 4765 636b 6f2f     rv:44.0) Gecko/\n3230 3130 3031 3031 2046 6972 6566 6f78    20100101 Firefox\n2f34 342e 300d 0a41 6363 6570 743a 2074    /44.0..Accept: t\n6578 742f 6874 6d6c 2c20 2a2f 2a3b 2071    ext/html, */*; q\n3d30 2e30 310d 0a41 6363 6570 742d 4c61    =0.01..Accept-La\n6e67 7561 6765 3a20 656e 2d55 532c 656e    nguage: en-US,en\n3b71 3d30 2e35 0d0a 4163 6365 7074 2d45    ;q=0.5..Accept-E\n6e63 6f64 696e 673a 2067 7a69 702c 2064    ncoding: gzip, d\n6566 6c61 7465 0d0a 444e 543a 2031 0d0a    eflate..DNT: 1..\n582d 5265 7175 6573 7465 642d 5769 7468    X-Requested-With\n3a20 584d 4c48 7474 7052 6571 7565 7374    : XMLHttpRequest\n0d0a 5265 6665 7265 723a 2068 7474 703a    ..Referer: http:\n2f2f 7374 6163 6b6f 7665 7266 6c6f 772e    //stackoverflow.\n636f 6d2f 7175 6573 7469 6f6e 732f 3239    com/questions/29\n3939 3034 3334 2f6a 6176 6173 6372 6970    990434/javascrip\n742d 6675 6e63 7469 6f6e 2d6f 7264 6572    t-function-order\n2d61 6a61 783f 6c71 3d31 0d0a 436f 6f6b    -ajax?lq=1..Cook\n6965 3a20 5f5f 6366 6475 6964 3d64 3936    ie: __cfduid=d96\n3530 3133 6335 6165 6239 6564 3234 3261    5013c5aeb9ed242a\n3466 6233 6439 6436 3064 3839 6538 3134    4fb3d9d60d89e814\n3537 3933 3333 3238 3b20 7072 6f76 3d34    57933328; prov=4\n6231 6163 3134 362d 3663 6132 2d34 6333    b1ac146-6ca2-4c3\n342d 6235 6366 2d36 6133 3331 3735 3061    4-b5cf-6a331750a\n3063 610d 0a43 6f6e 6e65 6374 696f 6e3a    <0ca class="Connection:"></0ca>\n206b 6565 702d 616c 6976 650d 0a0d 0a       keep-alive....'
packet3 = 'Ether: e0:f8:47:01:fd:6e -> 90:ef:68:ac:98:78\nIP DF 192.168.0.3 -> 104.16.112.18 \nTCP ack push 58341 -> 80\n    \n4745 5420 2f65 6f4e 6635 2e70 6e67 2048    GET /eoNf5.png H\n5454 502f 312e 310d 0a48 6f73 743a 2069    TTP/1.1..Host: i\n2e73 7461 636b 2e69 6d67 7572 2e63 6f6d    .stack.imgur.com\n0d0a 5573 6572 2d41 6765 6e74 3a20 4d6f    ..User-Agent: Mo\n7a69 6c6c 612f 352e 3020 284d 6163 696e    zilla/5.0 (Macin\n746f 7368 3b20 496e 7465 6c20 4d61 6320    tosh; Intel Mac \n4f53 2058 2031 302e 3131 3b20 7276 3a34    OS X 10.11; rv:4\n342e 3029 2047 6563 6b6f 2f32 3031 3030    4.0) Gecko/20100\n3130 3120 4669 7265 666f 782f 3434 2e30    101 Firefox/44.0\n0d0a 4163 6365 7074 3a20 696d 6167 652f    ..Accept: image/\n706e 672c 696d 6167 652f 2a3b 713d 302e    png,image/*;q=0.\n382c 2a2f 2a3b 713d 302e 350d 0a41 6363    8,*/*;q=0.5..Acc\n6570 742d 4c61 6e67 7561 6765 3a20 656e    ept-Language: en\n2d55 532c 656e 3b71 3d30 2e35 0d0a 4163    -US,en;q=0.5..Ac\n6365 7074 2d45 6e63 6f64 696e 673a 2067    cept-Encoding: g\n7a69 702c 2064 6566 6c61 7465 0d0a 444e    zip, deflate..DN\n543a 2031 0d0a 5265 6665 7265 723a 2068    T: 1..Referer: h\n7474 703a 2f2f 7374 6163 6b6f 7665 7266    ttp://stackoverf\n6c6f 772e 636f 6d2f 0d0a 436f 6f6b 6965    low.com/..Cookie\n3a20 5f5f 6366 6475 6964 3d64 6636 6365    : __cfduid=df6ce\n6631 3430 3865 3963 3133 3330 3135 6162    f1408e9c133015ab\n6431 6530 6366 3937 6461 3866 3134 3538    d1e0cf97da8f1458\n3032 3037 3237 3b20 5345 5353 494f 4e44    020727; SESSIOND\n4154 413d 2537 4225 3232 7365 7373 696f    ATA=%7B%22sessio\n6e43 6f75 6e74 2532 3225 3341 3125 3243    nCount%22%3A1%2C\n2532 3273 6573 7369 6f6e 5469 6d65 2532    %22sessionTime%2\n3225 3341 3134 3538 3130 3634 3037 3137    2%3A145810640717\n3725 3744 3b20 494d 4755 5255 4944 4a41    7%7D; IMGURUIDJA\n464f 3d37 3934 3762 3061 3834 3430 6433    FO=7947b0a8440d3\n3764 6232 3362 3437 3832 6335 3330 6333    7db23b4782c530c3\n3535 3566 6632 3937 3034 6266 3265 6635    555ff29704bf2ef5\n6561 3266 3733 6333 3930 6636 6562 6463    ea2f73c390f6ebdc\n3162 333b 2041 5a55 5345 523d 7565 312d    1b3; AZUSER=ue1-\n3037 3436 6138 3030 3831 3964 3462 3432    0746a800819d4b42\n6136 3830 3031 3132 6332 3265 3738 3032    a6800112c22e7802\n0d0a 436f 6e6e 6563 7469 6f6e 3a20 6b65    ..Connection: <ke></ke>\n6570 2d61 6c69 7665 0d0a 0d0a              ep-alive....'


class PacketUtilitiesTest(unittest.TestCase):

    def test_parse_packet_data_by_keyword(self):
        # GET, Host and Referer data for packet1
        packet1_text_only = packet_utilities.remove_hex_values_from_packet(packet1)

        desired_get_output1 = '/questions/89228/calling-an-external-command-in-python?rq=1'
        parsed_get_data1 = packet_utilities.parse_packet_data_by_keyword(packet1_text_only, 'GET')
        self.assertEquals(desired_get_output1, parsed_get_data1, 'Strings should be equal')

        desired_host_output1 = 'stackoverflow.com'
        parsed_host_data1 = packet_utilities.parse_packet_data_by_keyword(packet1_text_only, 'Host')
        self.assertEquals(desired_host_output1, parsed_host_data1, 'Strings should be equal')

        desired_host_output1 = 'http://stackoverflow.com/questions/33042846/email-bcc-recipients-not-hidden-using-python-smtplib?lq=1'
        parsed_referer_data1 = packet_utilities.parse_packet_data_by_keyword(packet1_text_only, 'Referer')
        self.assertEqual(desired_host_output1, parsed_referer_data1, 'Strings should be equal')

        # GET, Host and Referer data for packet2
        packet2_text_only = packet_utilities.remove_hex_values_from_packet(packet2)

        desired_get_output2 = '/topbar/site-switcher/site-list?_=1458106908924'
        parsed_get_data2 = packet_utilities.parse_packet_data_by_keyword(packet2_text_only, 'GET')
        self.assertEquals(desired_get_output2, parsed_get_data2, 'Strings should be equal')

        desired_host_output2 = 'stackoverflow.com'
        parsed_host_data2 = packet_utilities.parse_packet_data_by_keyword(packet2_text_only, 'Host')
        self.assertEquals(desired_host_output2, parsed_host_data2, 'Strings should be equal')

        desired_referer_output2 = 'http://stackoverflow.com/questions/29990434/javascript-function-order-ajax?lq=1'
        parsed_referer_data2 = packet_utilities.parse_packet_data_by_keyword(packet2_text_only, 'Referer')
        self.assertEqual(desired_referer_output2, parsed_referer_data2, 'Strings should be equal')

        # GET, Host and Referer data for packet3
        packet3_text_only = packet_utilities.remove_hex_values_from_packet(packet3)

        desired_get_output3 = '/eoNf5.png'
        parsed_get_data3 = packet_utilities.parse_packet_data_by_keyword(packet3_text_only, 'GET')
        self.assertEquals(desired_get_output3, parsed_get_data3, 'Strings should be equal')

        desired_host_output3 = 'i.stack.imgur.com'
        parsed_host_data3 = packet_utilities.parse_packet_data_by_keyword(packet3_text_only, 'Host')
        self.assertEquals(desired_host_output3, parsed_host_data3, 'Strings should be equal')

        desired_referer_output3 = 'http://stackoverflow.com/'
        parsed_referer_data3 = packet_utilities.parse_packet_data_by_keyword(packet3_text_only, 'Referer')
        self.assertEqual(desired_referer_output3, parsed_referer_data3, 'Strings should be equal')


if __name__ == '__main__':
    unittest.main()
