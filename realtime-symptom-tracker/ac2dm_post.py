import urllib, urllib2

# A little utility script to exercise c2dm from command line
url = 'https://android.apis.google.com/c2dm/send'
values = {'registration_id' : 'xxx',
	  'collapse_key' : 1,
	  'data.payload' : "payload"}		
print values
headers = {'Authorization': 'GoogleLogin auth=' + 'xxx'}
data = urllib.urlencode(values)
request = urllib2.Request(url, data, headers)

# Post
try:
	response = urllib2.urlopen(request)
	responseAsString = response.read()

except urllib2.HTTPError, e:
	print 'HTTPError ' + str(e)

print "response is "
print responseAsString
