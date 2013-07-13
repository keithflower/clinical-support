import requests, json

def name_to_nuid (name):
    # Request identifier for a drug by name
    s = 'http://rxnav.nlm.nih.gov/REST/Ndfrt/search?conceptName=xx&kindName=DRUG_KIND'.replace('xx', name)
    r = requests.get (s, headers={'Accept': 'application/json'})
    data = r.json()
    return data["groupConcepts"][0]['concept'][0]['conceptNui']

def interactions_json (drug_list):
    # Give a list of drug names, return interactions in json format
    nuids = [name_to_nuid (name) for name in drug_list]
    sl = '+'.join(nuids)
    s = 'http://rxnav.nlm.nih.gov/REST/Ndfrt/interaction?nuis=xx&scope=3'.replace('xx', sl)
    r = requests.get(s, headers={'Accept': 'application/json'})
    return r.json()

def extract_from_json (data, key, l=[]):
    # This is just a quick way to extract a list of value(s) from a key in
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

def interaction_p (data):
    # Were there interactions?
    if ([None] == extract_from_json (data, "fullInteractionGroup", l=[])):
        return False
    else:
        return True
    
def interaction_detail (interaction):
    # Returns just some pertinents details about interaction(s): the names
    # of the drugs with interactions, and the severity of the interaction
    l = []
    triplel = extract_from_json(interaction, "interactionTriple", l=[])
    for i in triplel:
        il = []
        il.append(extract_from_json(i, "conceptName", l=[])[2])
        il.append(extract_from_json(i, "severity", l=[]))
        l.append(il)
    return l

def interactions (drugs):
    j = interactions_json (drugs)
    if interaction_p (j):
        return interaction_detail(j)

print interactions (["rasagiline", "morphine", "doxepin"])
print interactions (["aspirin", "morphine", "doxepin"])
    
    
