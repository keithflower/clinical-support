Utilities to resolve name ambiguities
=====================================

Use the National Library of Medicine's RxNorm's RESTful API to try to resolve
potentially ambiguous drug names. A drug name as found in clinical documentation 
is sometimes misspelled, sometimes has quantities appended to it, etc. 

For example:

    resolve ("Ability10mg")

    [u'Abilify',
     u'Disintegrating Tablet',
     u'Oral Tablet',
     u'aripiprazole',
     u'aripiprazole 10 MG Oral Tablet [Abilify]',
     u'aripiprazole 10 MG Disintegrating Tablet [Abilify]',
     u'aripiprazole 10 MG [Abilify]',
     u'aripiprazole Oral Tablet [Abilify]',
     u'aripiprazole Disintegrating Tablet [Abilify]',
     u'aripiprazole 10 MG Oral Tablet',
     u'aripiprazole 10 MG Disintegrating Tablet',
     u'aripiprazole 10 MG',
     u'aripiprazole Oral Tablet',
     u'aripiprazole Disintegrating Tablet',
     u'aripiprazole Oral Product',
     u'aripiprazole Pill',
     u'aripiprazole Disintegrating Oral Product',
     u'Abilify Oral Product',
     u'Abilify Pill',
     u'Abilify Disintegrating Oral Product',
     u'Oral Product',
     u'Pill',
     u'Disintegrating Oral Product']
