Drug Interactions
=================

Using the NDF-RT's RESTful interface, extract drug interaction data:
 
    # Given a list of medications, show any interactions and the severity of the interaction
    print interactions (["rasagiline", "morphine", "doxepin"])
    >>> [[u'DOXEPIN/RASAGILINE', [u'Critical']], [u'MORPHINE/RASAGILINE', [u'Critical']]]

    print interactions (["aspirin", "morphine", "doxepin"])
    >>> None