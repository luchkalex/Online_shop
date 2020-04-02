<#import "parts/common.ftl" as c>

<@c.page>
    List of users

    <table>
        <thead>
        <tr>
            <th>Name</th>
            <th>Role</th>
            <th></th>
        </tr>
        </thead>
        <tbody>

        <#list userList as user>
            <tr>
                <td>${user.username}</td>
                <td><#list user.roles as role>${role}<#sep>, </#list>  </td>
                <td><a href="/users/${user.id}">Edit</a></td>
            </tr>
        </#list>
        </tbody>
    </table>
    <a href="/main">Main page</a>
</@c.page>