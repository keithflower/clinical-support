import requests, json

# Request all info in JSON

def name_to_id (name):
    # Request identifier for a drug by name
    s = 'http://rxnav.nlm.nih.gov/REST/Ndfrt/search?conceptName=xx&kindName=DRUG_KIND'.replace('xx', name)
    r = requests.get (s, headers={'Accept': 'application/json'})
    data = r.json()
    return data["groupConcepts"][0]['concept'][0]['conceptNui']

def interactions (drug_list):
    r = requests.get('http://rxnav.nlm.nih.gov/REST/Ndfrt/interaction/nui1=N0000007070&nui2=N0000179514&scope=2', headers={'Accept': 'application/json'})
    return r.json()

