# Use National Library of Medicine's RXNorm API to find medications which contain
# a given ingredient

import requests, json, urllib

def name_resolve_json (name):
    # Give a possibly ambiguous name, returns a list of possible matches ranked
    # by a likelihood score
    s = 'http://rxnav.nlm.nih.gov/REST/approximateTerm?term=xx&maxEntries=3'.replace('xx', urllib.quote(name))
    r = requests.get(s, headers={'Accept': 'application/json', 'charset': 'UTF-8'})
    return r.json()

def first_ranked_cui (approximates):
    # Just return the identifier for the best match by score
    return extract_from_json(approximates, "candidate", l=[])[0][0]['rxcui']

def contains (cui_l):
    # Give a list of ingredient identifiers, return drugs which contain
    # those ingredients
    sl = '+'.join(cui_l)
    s = 'http://rxnav.nlm.nih.gov/REST/brands?ingredientids=xx'.replace('xx', sl)
    r = requests.get(s, headers={'Accept': 'application/json', 'application/json' 'charset': 'UTF-8'})
    return r.json()

def extract_from_json (data, key, l=[]):
    # Extract a list of value(s) from a key in
    # a (potentially deeply) nested json object
    if isinstance(data, list):
        for item in data:
            extract_from_json(item, key, l)
    elif isinstance(data, dict):
        for k, v in data.iteritems():
            if (k == key):
                l.append(v)
            extract_from_json(v, key, l)
    return l

cv = contains([first_ranked_cui(name_resolve_json('acetaminophen'))])
cl = extract_from_json(cv, "name", l=[])
print "{0} medications contain acetaminophen:".format(len(cl))
print "\n".join(cl)
