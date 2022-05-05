# sendi Assaignment -Trending repositories on GitHub

App flow
[sendi-flow.png](https://postimg.cc/94DQ22SC)

#### Features
* Lis of most trending repositories from Github.
* Filter repositories by Day/Week/Month/.
* Save repositories as favorites.

#### App Architecture
Based on mvvm architecture and repository pattern.

#### The app includes the following main components:

* A local database that stores user's favorited repositories.
* A web api service.
* A repository that works with the database and the api service, providing a unified data interface.
* A ViewModel that provides data specific for the UI.
* The UI, which shows a visual representation of the data in the ViewModel.

#### Features not implemented
* UI that is better suitable for tablets - did not have time
