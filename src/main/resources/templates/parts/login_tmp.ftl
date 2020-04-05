<#macro login path isRegisterForm>


    <form action="${path}" method="post" novalidate>
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
        <#if isRegisterForm>
            <div class="form-group">
                <label for="email_input">Email</label>
                <input type="email" class="form-control col-sm-4" name="email" id="email_input"
                       placeholder="Email">
            </div>
        </#if>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <#if !isRegisterForm><a href="/registration" class="btn btn-primary my-3">Add new user</a></#if>
        <button type="submit" class="btn btn-primary"><#if isRegisterForm>Create user<#else >Sign In</#if></button>
    </form>



</#macro>

<#macro logout>
    <form action="/logout" method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <input type="submit" class="btn btn-primary " value="Sign Out"/>
    </form>
</#macro>