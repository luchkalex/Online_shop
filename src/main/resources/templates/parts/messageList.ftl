<#include "security.ftl">

<div class="card-columns">
    <#list messages as message>
        <div class="card my-2">
            <#if message.filename??>
                <img src="/img/${message.filename}" alt="Image" class="card-img-top">
            </#if>
            <div class="m-2">
                <span>${message.text}</span><br>
                <i>#${message.tag}</i>
            </div>

            <div class="card-footer text-muted">
                <a href="/users-messages/${message.author.id}"><strong>${message.author.username}</strong></a>
                <#if message.author.id == currentUserId>
                    <a href="/users-messages/${message.author.id}/?message=${message.id}"
                       class="btn btn-primary">Edit</a>
                </#if>
            </div>
        </div>

        <div>

        </div>
    <#else >
        No messages
    </#list>
</div>