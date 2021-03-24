Business requirements:

Develop web service for Gift Certificates system with the following entities (many-to-many): 

1. CreateDate, LastUpdateDate - format ISO 8601. Example: 2018-08-29T06:12:15.156. 
2. Duration - in days (expiration period)

The system should expose REST APIs to perform the following operations:

1. CRUD operations for GiftCertificate. If new tags are passed during creation/modification – they should be created in the DB. For update operation - update only fields, that pass in request, others should not be updated. Batch insert is out of scope.
2. CRD operations for Tag.
3. Get certificates with tags (all params are optional and can be used in conjunction):
- by tag name (ONE tag);
- search by part of name/description (can be implemented, using DB function call);
- sort by date or by name ASC/DESC (extra task: implement ability to apply both sort type at the same time).
