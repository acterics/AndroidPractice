from django.http import JsonResponse
from django.views.generic import ListView

from ServerApp.models import Author
from django.http import HttpResponse
from django.views.decorators.csrf import csrf_exempt


def index(request):
    return JsonResponse({'author': 'server', "message": 'hello, client'})


def registration(request):
    try:
        nickname = request.POST['nickname']
        try:
            Author.objects.get(nickname)
            HttpResponse('Nickname already exist')
        except Author.DoesNotExist:
            Author.objects.create(nickname)
    except KeyError:
        HttpResponse('ERROR')


def profile(request, nickname):
    return HttpResponse('hello ' + nickname)


@csrf_exempt
def login(request):
    try:
        nickname = request.POST['nickname']
        print(nickname)
        try:
            Author.objects.get(nickname=nickname)
            return JsonResponse({'response': 1, 'url': 'get_contacts_' + nickname, 'message': 'Connection successful'})
            # result = 'Hello, ' + nickname
        except Author.DoesNotExist:
            return JsonResponse({'response': 0, 'message': 'There\'s no such nickname'})
    except KeyError:
        return JsonResponse({'response': 0, 'message': 'Bad request'})


@csrf_exempt
def users_list(request, nickname):
    list_of_contacts = Author.objects.all().exclude(nickname=nickname)
    result = ''
    for contact in list_of_contacts:
        result += contact.nickname + '\n'
    return HttpResponse(result)


class Contacts(ListView):
    model = Author
    context_object_name = 'user_list'
    template_name = 'contacts.html'

    def get_queryset(self):
        return Author.objects.all().order_by('nickname')

