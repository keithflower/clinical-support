Find clinicians
===============

You might think that given the name of a patient's physician or other clinician that you'd like to contact, it would be relatively easy to find contact information, e.g., by searching the web.

However, doing a web search often brings up many links that require digging through extraneous info to get what you really want (address, fax, phone number, etc), or else the links don't contain any of the info you want at all.

Here's a little utility that queries a database that is always relatively up-to-date with clinician contact information, the National Provider Database. 

I wrote this because I like to run quickly queries like this right from my clinical documentation tools (or the command line) and have the results (optionally) inserted right into my documentation. 

For example, if I'm completing my evaluation note for a particular patient, it's convenient to be able to run the above query and have that information automatically inserted into my note:

    >>> npi_print_contact ("Edwin Flower")

        FLOWER
        EDWIN
        Psychiatry
        1639342769
        2166 HAYES ST
        SAN FRANCISCO
        CA
        94117
        4155483148 (phone)
        4153818557 (fax)

Thanks to Fred Trotter for making the API available. You can also just use [his site](http://docnpi.com) to do a relatively painless web query.


