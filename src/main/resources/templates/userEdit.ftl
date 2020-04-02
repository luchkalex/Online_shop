<#import "parts/common.ftl" as c>

<@c.page>

    <form action="/users" method="post">

        <p>User ID</p>
        <label>
            <input type="text" value="${user.id}" name="user_id" placeholder="id">
        </label>


        <p>Username</p>
        <label>
            <input type="text" value="${user.username}" name="username" placeholder="username">
        </label>


        <p>Password</p>
        <label>
            <input type="password" value="${user.password}" name="password" placeholder="password">
        </label>


        <p>Roles</p>
        <#list roles as role>
            <label><input type="checkbox" name="${role}" ${user.roles?seq_contains(role)?string("checked", "")}>${role}
            </label>
        </#list>

        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button type="submit">Save</button>
    </form>
    <br>
    <a href="/main">Main page</a>
</@c.page>