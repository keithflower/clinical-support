# npi_print_contact
#
#    Usage:
#
#    npi_print_contact ("<First name> <Last name>")
#
# This uses the public API at docnpi.com to retrieve physician
# contact information given a name. The motivation for
# this little utility is that even given a doctor's name,
# a web search often brings up many links that require
# digging through extraneous info to get what you really
# want (address, fax, phone number, etc), or else the
# links don't contain the info you want at all.
#
# Thanks to Fred Trotter for making the API available.
#
# Caveat: fails noisily when records not found (ie, add
#         your own error checking)

import requests, re 

def npi_search (fname, lname):
    # Grab a list of records matching names. This works kind of fuzzily
    # in that it looks like the API greps on the organization name as well
    # so typically you get a lot of names back where first name and last
    # name don't necessarily match the data's first and last
    # name fields. That's what name_match below will do.
    return requests.get(('http://docnpi.com/api/index.php' + \
                         '?first_name=xx' + \
                         '&last_name=yy' + \
                         '&org_name=' + \
                         '&address=' + \
                         '&state=&city_name=&zip=' + \
                         '&taxonomy=' + \
                         '&ident=' + \
                         '&is_person=true' + \
                         '&is_address=false' + \
                         '&is_org=false' + \
                         '&is_ident=false' + \
                         '&format=json')\
                        .replace('xx', fname).replace('yy', lname))\
                        .json()

def npi_name_match (records, fname, lname):
    # Filter for only records where names actually match
    # first and last name json fields
    matches = []
    for i in records:
        rec = records[i]
        if ((fname.upper() == rec["first_name"].upper()) and
            (lname.upper() == rec["last_name"].upper())):
            matches.append(rec)
    return matches

def npi_get_phone (npi):
    # Do simple scrape for data of interest of html from the
    # full NPI record
    phones = []
    r = requests.get("http://docnpi.com/npi/"+npi)
    phones.append(re.findall(re.escape \
                             ("td>Practice Location Address Telephone Number</td>\n\t\t<td>") \
                             +"(.*)" + \
                             re.escape("</td>"), \
                             r.content))
    return phones

def npi_contact_list (matches):
    # Return a contact list
    cl = []
    for i in matches:
        cl.append (i["last_name"])
        cl.append (i["first_name"])
        cl.append (i["description"])
        cl.append (i["npi"])
        cl.append (i["address"])
        cl.append (i["city"])
        cl.append (i["state"])
        cl.append (i["zip"])
        for p in npi_get_phone(i["npi"]):
            cl.append (p[0] + " (phone)")
        cl.append (i["fax"] + " (fax)")
    return cl

def npi_contact (name):
    # Return a contact list
    fname, lname  = name.split(' ')[0], name.split(' ')[1]
    a = npi_search (fname, lname)
    b = npi_name_match (a, fname, lname)
    return npi_contact_list (b)

def npi_print_contact (name):
    for i in npi_contact (name):
        print i
