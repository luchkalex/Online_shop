<#import "parts/common.ftl" as c>

<@c.page>
    <h2>${userChannel.username}</h2>

    <div>${type}</div>

    <ul class="list-group">
        <#list users as user>
            <li class="list-group-item"><a href="/users-messages/${user.id}">${user.username}</a></li>
        </#list>
    </ul>
</@c.page>