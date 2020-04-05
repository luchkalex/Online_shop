<#import "parts/common.ftl" as c>

<@c.page>
    <h5>${username}</h5>

    <form method="post" novalidate>
        <div class="form-group">
            <label for="username_input">User name</label>
            <input type="text" class="form-control col-sm-4" name="username" id="username_input"
                   placeholder="User name">
        </div>
        <div class="form-group">
            <label for="password_input">Password</label>
            <input type="password" class="form-control col-sm-4" name="password" id="password_input"
                   placeholder="Password">
        </div>
        <div class="form-group">
            <label for="email_input">Email</label>
            <input type="email" class="form-control col-sm-4" name="email" id="email_input" value="${email!''}"
                   placeholder="Email">
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button type="submit" class="btn btn-primary">Save</button>
    </form>
</@c.page>
