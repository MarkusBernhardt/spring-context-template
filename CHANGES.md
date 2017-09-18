CHANGES
=======

1.0.4
-----

* "resource" attribute property resolving. See #2. Many thanks to Mehmet.
* Beans without a name attribute still to be used with templating: "${" becomes "----" (four times "-") on id and "}" becomes "___" (four times "") on id. See #2. Many thanks to Mehmet.
* Anonymous beans are inserted only once, no exceptions. See #2. Many thanks to Mehmet.

1.0.3
-----

* Removed an unneeded import
* Fix the failed release of 1.0.2 to Maven Central

1.0.2
-----

* Added the ability to use anonymous beans and template placeholder resolution. See #1. Great thanks to Tomas for this patch. 

1.0.1
-----

* Changed attribute "location" to a more standard compliant "resource"
* Enabling nested templates
* Introducing attributes "name" and "value"

1.0.0
-----

* Initial release