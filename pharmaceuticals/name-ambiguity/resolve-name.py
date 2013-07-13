# Use the National Library of Medicine's RxNorm's RESTful API to try to resolve
# potentially ambiguous drug names, eg:
#
#   print resolve("murphine")
#
#    [u'Oral Tablet',
#     u'Morphine',
#     u'Morphine hydrochloride',
#     u'Morphine hydrochloride 40 MG Oral Tablet',
#     u'Morphine hydrochloride 40 MG',
#     u'Morphine Oral Tablet',
#     u'Morphine Pill',
#     u'Morphine Oral Product',
#     u'Oral Product',
#     u'Pill']
     
import requests, json, urllib

def name_resolve_json (name):
    # Give a possibly ambiguous name, returns a list of possible matches ranked
    # by a likelihood score
    s = 'http://rxnav.nlm.nih.gov/REST/approximateTerm?term=xx&maxEntries=3'.replace('xx', urllib.quote(name))
    r = requests.get(s, headers={'Accept': 'application/json'})
    return r.json()

def first_ranked_cui (approximates):
    # Just return the identifier for the best match by score
    return extract_from_json(approximates, "candidate", l=[])[0][0]['rxcui']

def all_related_from_cui (cui):
    # Return all related information for a given drug identifier
    s = 'http://rxnav.nlm.nih.gov/REST/rxcui/xx/allrelated'.replace('xx', cui)
    r = requests.get(s, headers={'Accept': 'application/json'})
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

def resolve (approximate):
    possibles = name_resolve_json(approximate)
    all = all_related_from_cui(first_ranked_cui(possibles))
    return extract_from_json(all, "name", l=[])


    
    
