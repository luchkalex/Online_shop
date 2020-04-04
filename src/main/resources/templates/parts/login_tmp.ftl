<#macro login path isRegisterForm>


    <form action="${path}" method="post">
        <div class="form-group">
            <label for="exampleInputEmail1">User name</label>
            <input type="text" class="form-control col-sm-4" name="username" id="exampleInputEmail1"
                   aria-describedby="emailHelp" placeholder="Enter email">
            <small id="emailHelp" class="form-text text-muted">We'll never share your email with anyone
                else.</small>
        </div>
        <div class="form-group">
            <label for="exampleInputPassword1">Password</label>
            <input type="password" class="form-control col-sm-4" name="password" id="exampleInputPassword1"
                   placeholder="Password">
        </div>
        <#if isRegisterForm>
            <div class="form-group">
                <label for="exampleInputPassword1">Email</label>
                <input type="email" class="form-control col-sm-4" name="email" id="exampleInputPassword1"
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