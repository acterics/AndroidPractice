from django.db import models


# Create your models here.

class Author(models.Model):
    nickname = models.CharField(max_length=50)

    def __str__(self):
        return self.nickname


class Message(models.Model):
    message_text = models.CharField(max_length=500)
    message_date = models.DateTimeField('sending_date')
    message_author = models.ForeignKey(Author, on_delete=models.CASCADE)

    def __str__(self):
        return self.message_date



