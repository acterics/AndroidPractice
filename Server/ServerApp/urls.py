from django.conf.urls import url
from ServerApp import views

urlpatterns = [
    url(r'^login', views.login),
    url(r'^registration', views.registration),
    url(r'^profile_(?P<nickname>[a-zA-Z]+)', views.profile),
    url(r'^get_contacts_(?P<nickname>[a-zA-Z]+)', views.users_list),
]
