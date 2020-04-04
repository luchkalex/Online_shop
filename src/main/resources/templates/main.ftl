<#import "parts/common.ftl" as c>

<@c.page>
    <div class="form-row">
        <form method="get" action="/main" class="form-inline">
            <label>
                <input type="text" name="filter" value="${filter ! ""}" placeholder="Find by Tag">
            </label>
            <button type="submit" class="ml-2 btn btn-primary">Найти</button>
        </form>
    </div>

    <a class="btn btn-primary my-3" data-toggle="collapse" href="#inputCollapse" role="button" aria-expanded="false"
       aria-controls="collapseExample">
        Add new message
    </a>
    <div class="collapse" id="inputCollapse">
        <div class="form-group">
            <form method="post" enctype="multipart/form-data">
                <label>
                    <input type="text" name="text" class="form-control my-1" placeholder="Введите сообщение"/>
                </label>
                <label>
                    <input type="text" name="tag" class="form-control my-1" placeholder="Тэг">
                </label>
                <div class="custom-file col-sm-4">
                    <input type="file" class="custom-file-input" id="customFile" name="file">
                    <label class="custom-file-label" for="customFile">Choose file</label>
                </div>

                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                <button type="submit" class="btn btn-primary my-1">Добавить</button>
            </form>
        </div>
    </div>



    <div class="card-columns">
        <#list messages as message>
            <div class="card my-2">
                <#if message.filename??>
                    <img src="/img/${message.filename}" alt="Image" class="card-img-top">
                </#if>
                <div class="m-2">
                    <span>${message.text}</span>
                    <i>${message.tag}</i>
                </div>

                <div class="card-footer text-muted">
                    <strong>${message.authorName}</strong>
                </div>
            </div>

            <div>

            </div>
        <#else >
            No messages
        </#list>
    </div>
</@c.page>