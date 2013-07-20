Find clinicians
===============

You'd think that given the name of a patient's physician/clinician, it would be relatively quick and easy to find contact information, e.g., by searching the web.

However, doing a web search often brings up many links that require digging through extraneous info to get what you really want (address, fax, phone number, etc), or else the links don't contain any of the info you want.

Here's a little utility that queries a database that's always relatively up-to-date with clinician contact information, the National Provider Database. 

I wrote this because I like to quickly run queries like this right from my clinical documentation tools (or the command line) and have the results (optionally) inserted right into documentation. 

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

I've also got some macros that parse this kind of information and automatically generate a letter, envelope, email, fax, etc.

Thanks to Fred Trotter for making the API available. You can also just use [his site](http://docnpi.com) to do a relatively painless web query.


