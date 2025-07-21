package com.example.versioncontrolappcomposable.ui.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.versioncontrolappcomposable.R
import com.example.versioncontrolappcomposable.data.models.Owner
import com.example.versioncontrolappcomposable.data.models.Repository
import com.example.versioncontrolappcomposable.data.models.User

import androidx.compose.material3.HorizontalDivider
import androidx.compose.ui.unit.Dp
import com.example.utilities.CoreUtils

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.LocalTextStyle
import androidx.compose.ui.text.style.LineHeightStyle
import com.example.versioncontrolappcomposable.data.models.GithubUser
import com.example.versioncontrolappcomposable.data.models.License

@Composable
fun HeaderTextComponent(textValue : String, textSize: TextUnit) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        text = textValue,
        style = TextStyle(
            fontSize = textSize,
            fontWeight = FontWeight.Medium
        )
    )
}

@Preview(showBackground = true)
@Composable
fun HeaderTextComponentPreview() {
    HeaderTextComponent("Home", 18.sp)
}


@Composable
fun TextComponent(textValue : String,
                  textSize: TextUnit,
                  isBold:Boolean = false,
                  isMediumBold:Boolean = false,
                  shouldCenter:Boolean = false,
                  color: Color = Color.Black) {
    Text(
        modifier = Modifier
            .wrapContentWidth()
            .wrapContentHeight(),
        text = textValue,
        style = TextStyle(
            fontSize = textSize,
            fontWeight = if (isBold) FontWeight.Bold else if (isMediumBold) FontWeight.Medium else FontWeight.Normal,
            textAlign = if (shouldCenter) TextAlign.Center else TextAlign.Left,
            color = color
        )
    )
}


@Preview(showBackground = true)
@Composable
fun TextComponentPreview() {
    TextComponent("Home", 10.sp)
}


@Composable
fun EditTextWithButton(
    buttonText: String,
    prompt: String,
    onButtonClick: (String) -> Unit
) {
    var text by remember { mutableStateOf("") }

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        value = text,
        leadingIcon = {
            Image(modifier = Modifier
                .width(20.dp)
                .height(20.dp),
                painter = painterResource(R.drawable.search_normal),
                contentDescription = null)
        },
        onValueChange = {
            text = it
        },

        placeholder = { Text(prompt) },

        trailingIcon = {
            Button(
                onClick = {
                    onButtonClick(text)
                    text = ""               // clear after send
                },
                enabled = text.isNotBlank(), // optional: disable if empty
                modifier = Modifier
                    .padding(end = 4.dp, top = 4.dp, bottom = 4.dp)
                    .background(Color(0xFF292D32))
                    .width(84.dp)
                    .height(38.dp)
            ) {
                Text(
                    text= buttonText,
                    color = Color.White,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        },
    )
}

@Preview(showBackground = true)
@Composable
fun EditTextWithButtonPreview()
{
    EditTextWithButton("Search", "Search for repo or user", {enteredText ->
        println("Entered text: $enteredText")
    })
}

@Composable
fun ImageAndTextComponent(image: Int, text:String)
{
    Column (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier.size(154.dp),
            painter = painterResource(image),
            contentDescription = "")
        TextComponent(textValue = text, textSize = 12.sp, isBold = false, shouldCenter = true)
    }
}

@Preview(showBackground = true)
@Composable
fun ImageAndTextComponentPreview() {
    ImageAndTextComponent(R.drawable.image_search, "We’ve searched the ends of the earth and we’ve not\nfound this user, please try again")
}


@Composable
fun LoadingOverlay(message: String = "Please wait...") {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f)), // Gray out with transparency
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = message,
                fontSize = 16.sp,
                color = Color.White
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoadingOverlayPreview() {
    LoadingOverlay()
}

@Composable
fun FixedTextFieldWithScrollableListForUsers(
    items: List<User>,
    onSearchButtonClicked:(String) -> Unit,
    onUserClicked : (User) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
            .background(Color.White)
    ) {

        // Fixed TextField at the top
        EditTextWithButton(
            buttonText = "Search",
            prompt = "Search for users...",
            onButtonClick = {onSearchButtonClicked(it)}
        )

        Spacer(modifier = Modifier.size(10.dp))

        // Scrollable LazyColumn
        LazyColumn(
            modifier = Modifier
                .fillMaxSize() // fills remaining space
        ) {
            items(items) { item ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, Color(0xFFD9D9D9), RectangleShape)
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        elevation = CardDefaults.cardElevation(5.dp),
                        shape = RectangleShape,
                        onClick = {onUserClicked(item)},
                    ) {
                        Column (modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White)
                            .padding(8.dp)
                            .wrapContentHeight()){

                            Row(modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()) {
                                AsyncImage(
                                    model = item.avatar_url,
                                    contentDescription = "",
                                    placeholder = painterResource(R.drawable.person),
                                    error = painterResource(R.drawable.person),
                                    modifier = Modifier
                                        .size(20.dp)
                                        .clip(CircleShape)
                                )

                                Spacer(modifier = Modifier.size(8.dp))
                                Column (modifier = Modifier
                                    .wrapContentWidth()
                                    .wrapContentHeight()){
                                    if (!item.name.isNullOrEmpty()) {
                                        TextComponent(textValue = item.name,
                                            textSize = 10.sp,
                                            isBold = false,
                                            shouldCenter = false,
                                            color = Color(0xFF408AAA)
                                        )
                                        Spacer(modifier = Modifier.size(2.dp))
                                    }

                                    if (item.login.isNotEmpty()) {
                                        TextComponent(
                                            textValue = item.login,
                                            textSize = 10.sp)
                                        Spacer(modifier = Modifier.size(4.dp))
                                    }

                                    if (item.bio?.isNotEmpty() == true) {
                                        TextComponent(
                                            textValue = item.bio,
                                            textSize = 10.sp)
                                        Spacer(modifier = Modifier.size(4.dp))
                                    }

                                    Row (
                                        modifier = Modifier
                                            .fillMaxWidth()) {

                                        if (item.location?.isNotEmpty() == true) {
                                            TextComponent(
                                                textValue = item.location,
                                                textSize = 8.sp,
                                                color = Color(0xFF727272))
                                            Spacer(modifier = Modifier.size(8.dp))
                                        }

                                        if (item.email?.isNotEmpty() == true) {
                                            TextComponent(
                                                textValue = item.email,
                                                textSize = 8.sp,
                                                color = Color(0xFF727272))
                                        }
                                    }

                                }

                            }

                        }
                    }
                }

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FixedTextFieldWithScrollableListForUsersPreview()
{
    FixedTextFieldWithScrollableListForUsers(listOf(User(login="festus971", id=104489016, node_id="U_kgDOBjpgOA", avatar_url="https://avatars.githubusercontent.com/u/104489016?v=4", gravatar_id=null, url="https://api.github.com/users/festus971", html_url="https://github.com/festus971", followers_url="https://api.github.com/users/festus971/followers", following_url="https://api.github.com/users/festus971/following{/other_user}", gists_url="https://api.github.com/users/festus971/gists{/gist_id}", starred_url="https://api.github.com/users/festus971/starred{/owner}{/repo}", subscriptions_url="https://api.github.com/users/festus971/subscriptions", organizations_url="https://api.github.com/users/festus971/orgs", repos_url="https://api.github.com/users/festus971/repos", events_url="https://api.github.com/users/festus971/events{/privacy}", received_events_url="https://api.github.com/users/festus971/received_events", type="User", user_view_type="public", site_admin=false, name="Festus Kiprop", company="@Kiprop", blog=null, location="@Nairobi", email=null, hireable=null, bio="Everyday is a new learning experience. What you take from your lessons is what you will have to combat the re-occurrence of the situation. @Kenneth G. Ortiz", twitter_username=null, public_repos=262, public_gists=0, followers=19, following=31, created_at="2022-04-27T07:20:48Z", updated_at="2025-07-13T10:21:07Z")), {}, {})
}

@Composable
fun UserProfileAndRepoComponent(user: User, repos:List<Repository>, onBackBtnSelected: ()->Unit)
{
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(Color.White)
            .padding(top = 31.dp, start = 20.dp, end = 20.dp)


    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Image(
                modifier = Modifier
                    .size(16.dp)
                    .clickable { onBackBtnSelected() },
                painter = painterResource(R.drawable.arrow_left),
                contentDescription = ""
            )

            Spacer(modifier = Modifier.size(8.dp))

            TextComponent(textValue = "Users", textSize = 12.sp, isMediumBold = true)

        }

        Spacer(modifier = Modifier.size(18.dp))

        Column (modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()) {
            Row(
                modifier = Modifier.fillMaxWidth()
                    .wrapContentHeight()
            ) {
                AsyncImage(
                    model = user.avatar_url,
                    contentDescription = "",
                    placeholder = painterResource(R.drawable.person),
                    error = painterResource(R.drawable.person),
                    modifier = Modifier
                        .size(45.dp)
                        .clip(CircleShape)
                )

                Spacer(modifier = Modifier.size(12.dp))

                Column (
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                ) {
                    if (!user.name.isNullOrEmpty()) {
                        TextComponent(textValue = user.name,
                            textSize = 16.sp,
                            isBold = false,
                            isMediumBold = true,
                            shouldCenter = false
                        )
                        Spacer(modifier = Modifier.size(2.dp))
                    }

                    if (user.login.isNotEmpty()) {
                        TextComponent(textValue = user.login,
                            textSize = 14.sp,
                            shouldCenter = false
                        )
                        Spacer(modifier = Modifier.size(2.dp))
                    }

                }
            }

            Spacer(modifier = Modifier.size(15.dp))

            if (user.bio?.isNotEmpty() == true) {
                TextComponent(textValue = user.bio,
                    textSize = 12.sp,
                    shouldCenter = false
                )
                Spacer(modifier = Modifier.size(10.dp))
            }

            if (user.url.isNotEmpty() || !user.location.isNullOrEmpty()) {
                Row(
                    modifier = Modifier
                        .height(15.dp)
                        .fillMaxWidth()
                ) {
                    if (!user.location.isNullOrEmpty()) {
                        ImageAndIconInRowComponent(R.drawable.location, user.location)
                        Spacer(modifier = Modifier.size(8.dp))
                    }

                    if (user.url.isNotEmpty()) {
                        ImageAndIconInRowComponent(R.drawable.url_logo, user.url)
                    }

                }

                Spacer(modifier = Modifier.size(10.dp))
            }

            Row(
                modifier = Modifier
                    .height(15.dp)
                    .fillMaxWidth()
            ) {
                ImageAndIconInRowComponent(R.drawable.people, user.followers.toString() + " followers.")

                Spacer(modifier = Modifier.size(8.dp))

                TextComponent(textValue = user.following.toString() + " following", textSize = 10.sp, color = Color(0xFF1A1A1A))

            }

            Spacer(modifier = Modifier.size(20.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                TextComponent(textValue = "Repositories", textSize = 10.sp, isMediumBold = true)
                Spacer(modifier = Modifier.size(2.dp))

                Text(
                    fontSize = 8.sp,
                    style = LocalTextStyle.current.copy(lineHeight = 8.sp),
                    modifier = Modifier
                    .background(
                        shape = RoundedCornerShape(8.dp),
                        color = Color(0xFFF1F1F1)
                    )
                    .padding(start = 5.dp, end = 5.dp, top = 2.dp, bottom = 2.dp),
                    text = user.public_repos.toString()

                )

            }

            Spacer(modifier = Modifier.size(8.dp))

            Row(modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()) {
                HorizontalDivider(modifier = Modifier.weight(1f), thickness = 2.dp, color = Color.Black)
                HorizontalDivider(modifier = Modifier.weight(3f), thickness = 1.5.dp, color = Color(0xFFF1F1F1))
            }

            if (repos.isNotEmpty()) {
                //display repo details
                Spacer(modifier = Modifier.size(10.dp))
                UserPageRepoLazyColumn(repos)
            } else {
                ImageAndTextComponent(R.drawable.image_no_repo, "This user doesn’t have repositories yet, come back later :-)")
            }
            
        }

    }

}

@Preview(showBackground = true)
@Composable
fun UserProfileAndRepoComponentPreview()
{
    UserProfileAndRepoComponent(User(login="festus971", id=104489016, node_id="U_kgDOBjpgOA", avatar_url="https://avatars.githubusercontent.com/u/104489016?v=4", gravatar_id=null, url="https://api.github.com/users/festus971", html_url="https://github.com/festus971", followers_url="https://api.github.com/users/festus971/followers", following_url="https://api.github.com/users/festus971/following{/other_user}", gists_url="https://api.github.com/users/festus971/gists{/gist_id}", starred_url="https://api.github.com/users/festus971/starred{/owner}{/repo}", subscriptions_url="https://api.github.com/users/festus971/subscriptions", organizations_url="https://api.github.com/users/festus971/orgs", repos_url="https://api.github.com/users/festus971/repos", events_url="https://api.github.com/users/festus971/events{/privacy}", received_events_url="https://api.github.com/users/festus971/received_events", type="User", user_view_type="public", site_admin=false, name="Festus Kiprop", company="@Kiprop", blog=null, location="@Nairobi", email=null, hireable=null, bio="Everyday is a new learning experience. What you take from your lessons is what you will have to combat the re-occurrence of the situation. @Kenneth G. Ortiz", twitter_username=null, public_repos=262, public_gists=0, followers=19, following=31, created_at="2022-04-27T07:20:48Z", updated_at="2025-07-13T10:21:07Z"),
        listOf(Repository(id = 491577864, node_id = "R_kgDOHUziCA", name = "A-quiz-Board-template", full_name = "festus971/A-quiz-Board-template", private = false, GithubUser(
                login = "festus971",
                id = 104489016,
                node_id = "U_kgDOBjpgOA",
                avatar_url = "https://avatars.githubusercontent.com/u/104489016?v=4",
                gravatar_id = "",
                url = "https://api.github.com/users/festus971",
                html_url = "https://github.com/festus971",
                followers_url = "https://api.github.com/users/festus971/followers",
                following_url = "https://api.github.com/users/festus971/following{/other_user}",
                gists_url = "https://api.github.com/users/festus971/gists{/gist_id}",
                starred_url = "https://api.github.com/users/festus971/starred{/owner}{/repo}",
                subscriptions_url = "https://api.github.com/users/festus971/subscriptions",
                organizations_url = "https://api.github.com/users/festus971/orgs",
                repos_url = "https://api.github.com/users/festus971/repos",
                events_url = "https://api.github.com/users/festus971/events{/privacy}",
                received_events_url = "https://api.github.com/users/festus971/received_events",
                type = "User",
                user_view_type = "public",
                site_admin = false), html_url = "https://github.com/festus971/A-quiz-Board-template", description = "This is my third independent project as a student in moringa school. its a quiz board webpage ", fork = false, url = "https://api.github.com/repos/festus971/A-quiz-Board-template", forks_url = "https://api.github.com/repos/festus971/A-quiz-Board-template/forks", keys_url = "https://api.github.com/repos/festus971/A-quiz-Board-template/keys{/key_id}", collaborators_url = "https://api.github.com/repos/festus971/A-quiz-Board-template/collaborators{/collaborator}", teams_url = "https://api.github.com/repos/festus971/A-quiz-Board-template/teams", hooks_url = "https://api.github.com/repos/festus971/A-quiz-Board-template/hooks", issue_events_url = "https://api.github.com/repos/festus971/A-quiz-Board-template/issues/events{/number}", events_url = "https://api.github.com/repos/festus971/A-quiz-Board-template/events", assignees_url = "https://api.github.com/repos/festus971/A-quiz-Board-template/assignees{/user}", branches_url = "https://api.github.com/repos/festus971/A-quiz-Board-template/branches{/branch}", tags_url = "https://api.github.com/repos/festus971/A-quiz-Board-template/tags", blobs_url = "https://api.github.com/repos/festus971/A-quiz-Board-template/git/blobs{/sha}", git_tags_url = "https://api.github.com/repos/festus971/A-quiz-Board-template/git/tags{/sha}", git_refs_url = "https://api.github.com/repos/festus971/A-quiz-Board-template/git/refs{/sha}", trees_url = "https://api.github.com/repos/festus971/A-quiz-Board-template/git/trees{/sha}", statuses_url = "https://api.github.com/repos/festus971/A-quiz-Board-template/statuses/{sha}", languages_url = "https://api.github.com/repos/festus971/A-quiz-Board-template/languages", stargazers_url = "https://api.github.com/repos/festus971/A-quiz-Board-template/stargazers", contributors_url = "https://api.github.com/repos/festus971/A-quiz-Board-template/contributors", subscribers_url = "https://api.github.com/repos/festus971/A-quiz-Board-template/subscribers", subscription_url = "https://api.github.com/repos/festus971/A-quiz-Board-template/subscription", commits_url = "https://api.github.com/repos/festus971/A-quiz-Board-template/commits{/sha}", git_commits_url = "https://api.github.com/repos/festus971/A-quiz-Board-template/git/commits{/sha}", comments_url = "https://api.github.com/repos/festus971/A-quiz-Board-template/comments{/number}", issue_comment_url = "https://api.github.com/repos/festus971/A-quiz-Board-template/issues/comments{/number}", contents_url = "https://api.github.com/repos/festus971/A-quiz-Board-template/contents/{+path}", compare_url = "https://api.github.com/repos/festus971/A-quiz-Board-template/compare/{base}...{head}", merges_url = "https://api.github.com/repos/festus971/A-quiz-Board-template/merges", archive_url = "https://api.github.com/repos/festus971/A-quiz-Board-template/{archive_format}{/ref}", downloads_url = "https://api.github.com/repos/festus971/A-quiz-Board-template/downloads", issues_url = "https://api.github.com/repos/festus971/A-quiz-Board-template/issues{/number}", pulls_url = "https://api.github.com/repos/festus971/A-quiz-Board-template/pulls{/number}", milestones_url = "https://api.github.com/repos/festus971/A-quiz-Board-template/milestones{/number}", notifications_url = "https://api.github.com/repos/festus971/A-quiz-Board-template/notifications{?since,all,participating}", labels_url = "https://api.github.com/repos/festus971/A-quiz-Board-template/labels{/name}", releases_url = "https://api.github.com/repos/festus971/A-quiz-Board-template/releases{/id}", deployments_url = "https://api.github.com/repos/festus971/A-quiz-Board-template/deployments", created_at = "2022-05-12T15:51:57Z", updated_at = "2022-05-26T17:38:48Z", pushed_at = "2022-05-16T08:12:48Z", git_url = "git://github.com/festus971/A-quiz-Board-template.git", ssh_url = "git@github.com:festus971/A-quiz-Board-template.git", clone_url = "https://github.com/festus971/A-quiz-Board-template.git", svn_url = "https://github.com/festus971/A-quiz-Board-template", homepage = "https://festus971.github.io/A-quiz-Board-template/", size = 17, stargazers_count = 0, watchers_count = 0, language = "HTML", has_issues = true, has_projects = true, has_downloads = true, has_wiki = true, has_pages = true, has_discussions = false, forks_count = 0, mirror_url = null, archived = false, disabled = false, open_issues_count = 0, license = null, allow_forking = true, is_template = false, web_commit_signoff_required = false, visibility = "public", forks = 0, open_issues = 0, watchers = 0, default_branch = "master", topics = listOf(""), score= 1.0)),
                {}
    )
}


@Composable
fun ImageAndIconInRowComponent(
    image:Int, text:String,
    imageSize: Dp = 15.dp,
    spacing: Dp = 6.dp,
    textSize:TextUnit = 10.sp,
    color:Color = Color(0xFF1A1A1A))
{
    Row (
        modifier = Modifier
            .wrapContentWidth()
            .wrapContentHeight()
    ) {
        Image(
            modifier = Modifier.size(imageSize),
            painter = painterResource(image),
            contentDescription = ""
        )
        Spacer(modifier = Modifier.size(spacing))
        TextComponent(textValue = text, textSize = textSize, color = color)

    }
}

@Preview(showBackground = true)
@Composable
fun ImageAndIconInRowComponentPreview()
{
    ImageAndIconInRowComponent(R.drawable.location, "Lagos, Nigeria")
}


@Composable
fun UserPageRepoLazyColumn(items: List<Repository>)
{
    // Scrollable LazyColumn
    LazyColumn(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()

    ) {
        items(items) { item ->
            Box(
                modifier = Modifier
                    .background(Color.White)
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .border(1.dp, Color(0xFFD9D9D9), RectangleShape)
            ) {
                Column(
                    modifier = Modifier.background(Color.White)
                        .padding(12.dp)
                ) {
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                    ) {
                        TextComponent(
                            textValue = item.owner.login + "/",
                            textSize = 12.sp,
                            isMediumBold = true,
                            color = Color(0xFF544177))

                        TextComponent(
                            textValue = item.name,
                            textSize = 12.sp,
                            isMediumBold = true,
                            color = Color.Black
                        )

                        Spacer(modifier = Modifier.size(9.dp))

                        Text(
                            fontSize = 8.sp,
                            style = LocalTextStyle.current.copy(lineHeight = 8.sp),
                            modifier = Modifier
                            .border(0.5.dp, Color(0xFFD9D9D9), shape = RoundedCornerShape(5.dp))
                            .background(
                                shape = RoundedCornerShape(5.dp),
                                color = Color(0xFFF1F1F1)
                            )

                            .padding(start = 8.dp, end = 8.dp, top = 3.dp, bottom = 3.dp),
                            text = item.visibility
                        )

                        Spacer(modifier = Modifier.weight(1f))

                        ImageAndIconInRowComponent(
                            R.drawable.star,
                            imageSize = 12.dp,
                            text = item.stargazers_count.toString(),
                            spacing = 1.dp,
                            textSize = 10.sp,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.size(13.dp))
                        item.language?.let {
                            ImageAndIconInRowComponent(
                                R.drawable.ellipse,
                                imageSize = 12.dp,
                                text = it,
                                spacing = 1.dp,
                                textSize = 10.sp,
                                color = Color.Black
                            )
                        }
                    }

                    Spacer(modifier = Modifier.size(11.dp))

                    item.description?.let {
                        TextComponent(
                            textValue = it,
                            textSize = 10.sp,
                            color = Color(0xFF292D32)
                        )
                    }

                    val daysAgo = CoreUtils.getTimeDifferenceInDays(item.updated_at)
                    Spacer(modifier = Modifier.size(7.dp))

                    TextComponent(
                        textValue = "Updated " + daysAgo.toString() + " days ago",
                        textSize = 10.sp,
                        color = Color(0xFF1A1A1A)
                    )

                }


            }

            Spacer(modifier = Modifier.size(5.dp))


        }
    }

}

@Preview(showBackground = true)
@Composable
fun UserPageRepoLazyColumnPreview() {
    UserPageRepoLazyColumn(listOf(Repository(id = 491577864, node_id = "R_kgDOHUziCA", name = "A-quiz-Board-template", full_name = "festus971/A-quiz-Board-template", private = false, GithubUser(
        login = "festus971",
        id = 104489016,
        node_id = "U_kgDOBjpgOA",
        avatar_url = "https://avatars.githubusercontent.com/u/104489016?v=4",
        gravatar_id = "",
        url = "https://api.github.com/users/festus971",
        html_url = "https://github.com/festus971",
        followers_url = "https://api.github.com/users/festus971/followers",
        following_url = "https://api.github.com/users/festus971/following{/other_user}",
        gists_url = "https://api.github.com/users/festus971/gists{/gist_id}",
        starred_url = "https://api.github.com/users/festus971/starred{/owner}{/repo}",
        subscriptions_url = "https://api.github.com/users/festus971/subscriptions",
        organizations_url = "https://api.github.com/users/festus971/orgs",
        repos_url = "https://api.github.com/users/festus971/repos",
        events_url = "https://api.github.com/users/festus971/events{/privacy}",
        received_events_url = "https://api.github.com/users/festus971/received_events",
        type = "User",
        user_view_type = "public",
        site_admin = false
    ), html_url = "https://github.com/festus971/A-quiz-Board-template", description = "This is my third independent project as a student in moringa school. its a quiz board webpage ", fork = false, url = "https://api.github.com/repos/festus971/A-quiz-Board-template", forks_url = "https://api.github.com/repos/festus971/A-quiz-Board-template/forks", keys_url = "https://api.github.com/repos/festus971/A-quiz-Board-template/keys{/key_id}", collaborators_url = "https://api.github.com/repos/festus971/A-quiz-Board-template/collaborators{/collaborator}", teams_url = "https://api.github.com/repos/festus971/A-quiz-Board-template/teams", hooks_url = "https://api.github.com/repos/festus971/A-quiz-Board-template/hooks", issue_events_url = "https://api.github.com/repos/festus971/A-quiz-Board-template/issues/events{/number}", events_url = "https://api.github.com/repos/festus971/A-quiz-Board-template/events", assignees_url = "https://api.github.com/repos/festus971/A-quiz-Board-template/assignees{/user}", branches_url = "https://api.github.com/repos/festus971/A-quiz-Board-template/branches{/branch}", tags_url = "https://api.github.com/repos/festus971/A-quiz-Board-template/tags", blobs_url = "https://api.github.com/repos/festus971/A-quiz-Board-template/git/blobs{/sha}", git_tags_url = "https://api.github.com/repos/festus971/A-quiz-Board-template/git/tags{/sha}", git_refs_url = "https://api.github.com/repos/festus971/A-quiz-Board-template/git/refs{/sha}", trees_url = "https://api.github.com/repos/festus971/A-quiz-Board-template/git/trees{/sha}", statuses_url = "https://api.github.com/repos/festus971/A-quiz-Board-template/statuses/{sha}", languages_url = "https://api.github.com/repos/festus971/A-quiz-Board-template/languages", stargazers_url = "https://api.github.com/repos/festus971/A-quiz-Board-template/stargazers", contributors_url = "https://api.github.com/repos/festus971/A-quiz-Board-template/contributors", subscribers_url = "https://api.github.com/repos/festus971/A-quiz-Board-template/subscribers", subscription_url = "https://api.github.com/repos/festus971/A-quiz-Board-template/subscription", commits_url = "https://api.github.com/repos/festus971/A-quiz-Board-template/commits{/sha}", git_commits_url = "https://api.github.com/repos/festus971/A-quiz-Board-template/git/commits{/sha}", comments_url = "https://api.github.com/repos/festus971/A-quiz-Board-template/comments{/number}", issue_comment_url = "https://api.github.com/repos/festus971/A-quiz-Board-template/issues/comments{/number}", contents_url = "https://api.github.com/repos/festus971/A-quiz-Board-template/contents/{+path}", compare_url = "https://api.github.com/repos/festus971/A-quiz-Board-template/compare/{base}...{head}", merges_url = "https://api.github.com/repos/festus971/A-quiz-Board-template/merges", archive_url = "https://api.github.com/repos/festus971/A-quiz-Board-template/{archive_format}{/ref}", downloads_url = "https://api.github.com/repos/festus971/A-quiz-Board-template/downloads", issues_url = "https://api.github.com/repos/festus971/A-quiz-Board-template/issues{/number}", pulls_url = "https://api.github.com/repos/festus971/A-quiz-Board-template/pulls{/number}", milestones_url = "https://api.github.com/repos/festus971/A-quiz-Board-template/milestones{/number}", notifications_url = "https://api.github.com/repos/festus971/A-quiz-Board-template/notifications{?since,all,participating}", labels_url = "https://api.github.com/repos/festus971/A-quiz-Board-template/labels{/name}", releases_url = "https://api.github.com/repos/festus971/A-quiz-Board-template/releases{/id}", deployments_url = "https://api.github.com/repos/festus971/A-quiz-Board-template/deployments", created_at = "2022-05-12T15:51:57Z", updated_at = "2022-05-26T17:38:48Z", pushed_at = "2022-05-16T08:12:48Z", git_url = "git://github.com/festus971/A-quiz-Board-template.git", ssh_url = "git@github.com:festus971/A-quiz-Board-template.git", clone_url = "https://github.com/festus971/A-quiz-Board-template.git", svn_url = "https://github.com/festus971/A-quiz-Board-template", homepage = "https://festus971.github.io/A-quiz-Board-template/", size = 17, stargazers_count = 0, watchers_count = 0, language = "HTML", has_issues = true, has_projects = true, has_downloads = true, has_wiki = true, has_pages = true, has_discussions = false, forks_count = 0, mirror_url = null, archived = false, disabled = false, open_issues_count = 0, license = null, allow_forking = true, is_template = false, web_commit_signoff_required = false, visibility = "public", forks = 0, open_issues = 0, watchers = 0, default_branch = "master", topics= listOf(""), score= 1.0 )))
}



@Composable
fun RepoPageLazyColumn(items: List<Repository>)
{
    // Scrollable LazyColumn
    LazyColumn(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()

    ) {
        items(items) { item ->

                Card(
                    modifier = Modifier
                        .background(Color.White)
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    elevation = CardDefaults.cardElevation(5.dp),
                    shape = RectangleShape,
                )
                {
                    Column(
                        modifier = Modifier.background(Color.White)
                            .padding(12.dp)
                    ) {
                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                        ) {
                            AsyncImage(
                                model = item.owner.avatar_url,
                                contentDescription = "",
                                placeholder = painterResource(R.drawable.person),
                                error = painterResource(R.drawable.person),
                                modifier = Modifier
                                    .size(20.dp)
                                    .clip(CircleShape)
                            )
                            Spacer(modifier = Modifier.size(8.dp))
                            TextComponent(
                                textValue = item.owner.login + "/",
                                textSize = 12.sp,
                                isMediumBold = true,
                                color = Color(0xFF544177))

                            TextComponent(
                                textValue = item.name,
                                textSize = 12.sp,
                                isMediumBold = true,
                                color = Color.Black
                            )

                            Spacer(modifier = Modifier.weight(1f))

                            ImageAndIconInRowComponent(
                                R.drawable.star,
                                imageSize = 12.dp,
                                text = item.stargazers_count.toString(),
                                spacing = 1.dp,
                                textSize = 10.sp,
                                color = Color.Black
                            )
                            Spacer(modifier = Modifier.size(13.dp))
                            item.language?.let {
                                ImageAndIconInRowComponent(
                                    R.drawable.ellipse,
                                    imageSize = 12.dp,
                                    text = it,
                                    spacing = 1.dp,
                                    textSize = 10.sp,
                                    color = Color.Black
                                )
                            }
                        }

                        Spacer(modifier = Modifier.size(11.dp))

                        item.description?.let {
                            TextComponent(
                                textValue = it,
                                textSize = 10.sp,
                                color = Color(0xFF292D32)
                            )
                        }

                        Spacer(modifier = Modifier.size(16.dp))

                        ScrollableRowComposable(item.topics)

                    }


                }

            Spacer(modifier = Modifier.size(5.dp))
        }
    }

}


@Composable
fun EditTextFieldWithScrollableListForRepos(
    items: List<Repository>,
    onSearchButtonClicked:(String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(20.dp)

    ) {

        // Fixed TextField at the top
        EditTextWithButton(
            buttonText = "Search",
            prompt = "Search for repositories...",
            onButtonClick = {onSearchButtonClicked(it)}
        )

        Spacer(modifier = Modifier.size(14.dp))

        RepoPageLazyColumn(items)
    }
}

@Preview(showBackground = true)
@Composable
fun EditTextFieldWithScrollableListForReposPreview() {
    EditTextFieldWithScrollableListForRepos(listOf(Repository(id= 117221717, node_id = "MDEwOlJlcG9zaXRvcnkxMTcyMjE3MTc=",
        name ="electrum-merchant",
    full_name= "spesmilo/electrum-merchant",
    private= false,
    owner= GithubUser(login= "spesmilo", id= 2084673, node_id = "MDEyOk9yZ2FuaXphdGlvbjIwODQ2NzM=",
    avatar_url= "https://avatars.githubusercontent.com/u/2084673?v=4",
    gravatar_id= "",
    url= "https://api.github.com/users/spesmilo",

    html_url= "https://github.com/spesmilo",
    followers_url= "https://api.github.com/users/spesmilo/followers",
    following_url= "https://api.github.com/users/spesmilo/following{/other_user}",
    gists_url= "https://api.github.com/users/spesmilo/gists{/gist_id}",
    starred_url= "https://api.github.com/users/spesmilo/starred{/owner}{/repo}",
    subscriptions_url= "https://api.github.com/users/spesmilo/subscriptions",
    organizations_url= "https://api.github.com/users/spesmilo/orgs",
    repos_url= "https://api.github.com/users/spesmilo/repos",
    events_url= "https://api.github.com/users/spesmilo/events{/privacy}",
    received_events_url= "https://api.github.com/users/spesmilo/received_events",
    type= "Organization",
    user_view_type= "public",
    site_admin= false),
    html_url= "https://github.com/spesmilo/electrum-merchant",
    description= "configuration script for merchant website",
    fork= false,
    url= "https://api.github.com/repos/spesmilo/electrum-merchant",
    forks_url= "https://api.github.com/repos/spesmilo/electrum-merchant/forks",
    keys_url= "https://api.github.com/repos/spesmilo/electrum-merchant/keys{/key_id}",
    collaborators_url= "https://api.github.com/repos/spesmilo/electrum-merchant/collaborators{/collaborator}",
    teams_url= "https://api.github.com/repos/spesmilo/electrum-merchant/teams",
    hooks_url= "https://api.github.com/repos/spesmilo/electrum-merchant/hooks",
    issue_events_url= "https://api.github.com/repos/spesmilo/electrum-merchant/issues/events{/number}",
    events_url= "https://api.github.com/repos/spesmilo/electrum-merchant/events",
    assignees_url= "https://api.github.com/repos/spesmilo/electrum-merchant/assignees{/user}",
    branches_url= "https://api.github.com/repos/spesmilo/electrum-merchant/branches{/branch}",
    tags_url= "https://api.github.com/repos/spesmilo/electrum-merchant/tags",
    blobs_url= "https://api.github.com/repos/spesmilo/electrum-merchant/git/blobs{/sha}",
    git_tags_url= "https://api.github.com/repos/spesmilo/electrum-merchant/git/tags{/sha}",
    git_refs_url= "https://api.github.com/repos/spesmilo/electrum-merchant/git/refs{/sha}",
    trees_url= "https://api.github.com/repos/spesmilo/electrum-merchant/git/trees{/sha}",
    statuses_url= "https://api.github.com/repos/spesmilo/electrum-merchant/statuses/{sha}",
    languages_url= "https://api.github.com/repos/spesmilo/electrum-merchant/languages",
    stargazers_url= "https://api.github.com/repos/spesmilo/electrum-merchant/stargazers",
    contributors_url= "https://api.github.com/repos/spesmilo/electrum-merchant/contributors",
    subscribers_url= "https://api.github.com/repos/spesmilo/electrum-merchant/subscribers",
    subscription_url= "https://api.github.com/repos/spesmilo/electrum-merchant/subscription",
    commits_url= "https://api.github.com/repos/spesmilo/electrum-merchant/commits{/sha}",
    git_commits_url= "https://api.github.com/repos/spesmilo/electrum-merchant/git/commits{/sha}",
    comments_url= "https://api.github.com/repos/spesmilo/electrum-merchant/comments{/number}",
    issue_comment_url= "https://api.github.com/repos/spesmilo/electrum-merchant/issues/comments{/number}",
    contents_url= "https://api.github.com/repos/spesmilo/electrum-merchant/contents/{+path}",
    compare_url= "https://api.github.com/repos/spesmilo/electrum-merchant/compare/{base}...{head}",
    merges_url= "https://api.github.com/repos/spesmilo/electrum-merchant/merges",
    archive_url= "https://api.github.com/repos/spesmilo/electrum-merchant/{archive_format}{/ref}",
    downloads_url= "https://api.github.com/repos/spesmilo/electrum-merchant/downloads",
    issues_url= "https://api.github.com/repos/spesmilo/electrum-merchant/issues{/number}",
    pulls_url= "https://api.github.com/repos/spesmilo/electrum-merchant/pulls{/number}",
    milestones_url= "https://api.github.com/repos/spesmilo/electrum-merchant/milestones{/number}",
    notifications_url= "https://api.github.com/repos/spesmilo/electrum-merchant/notifications{?since,all,participating}",
    labels_url= "https://api.github.com/repos/spesmilo/electrum-merchant/labels{/name}",
    releases_url= "https://api.github.com/repos/spesmilo/electrum-merchant/releases{/id}",
    deployments_url= "https://api.github.com/repos/spesmilo/electrum-merchant/deployments",
    created_at= "2018-01-12T09:31:05Z",
    updated_at= "2025-01-07T17:24:34Z",
    pushed_at= "2019-02-11T13:50:39Z",
    git_url= "git://github.com/spesmilo/electrum-merchant.git",
    ssh_url= "git@github.com:spesmilo/electrum-merchant.git",
    clone_url= "https://github.com/spesmilo/electrum-merchant.git",
    svn_url= "https://github.com/spesmilo/electrum-merchant",
    homepage= null,
    size= 31,
    stargazers_count= 32,
    watchers_count= 32,
    language= "Shell",
    has_issues= true,
    has_projects= false,
    has_downloads= true,
    has_wiki= false,
    has_pages= false,
    has_discussions= false,
    forks_count= 20,
    mirror_url= null,
    archived= true,
    disabled= false,
    open_issues_count= 1,
    license= License(
        key= "mit",
        name= "MIT License",
        spdx_id= "MIT",
        url= "https://api.github.com/licenses/mit",
        node_id= "MDc6TGljZW5zZTEz"),
    allow_forking= true,
    is_template= false,
    web_commit_signoff_required= false,
    topics= listOf(""),
    visibility= "public",
    forks= 20,
    open_issues= 1,
    watchers= 32,
    default_branch= "master",
    score= 1.0)), {})
}


@Composable
fun ScrollableRowComposable(items: List<String>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
            .padding(8.dp)
    ) {
        items.forEachIndexed { index, item ->

            Text(modifier = Modifier
                .heightIn(22.dp)
                .wrapContentWidth()
                .background(
                    shape = RoundedCornerShape(8.dp),
                    color = Color(0xFFE8F9FF)
                )
                .padding(horizontal = 12.dp, vertical = 4.dp),
                text = item,
                fontSize = 10.sp
            )

            // Add spacer between items except after the last one
            if (index != items.lastIndex) {
                Spacer(modifier = Modifier.width(6.dp))
            }
        }
    }
}




