export const Game = () => ({
  id: '',
  title: '',
  description:'',
  platforms: [],
  genres: [],
  publicationDate: null,
  ageRating: '',
  developer: '',
  publisher: '',
  gameImage: '',
  reviews: [Review()]
})

export const Review = () => ({
  id: '',
  reviewHeadline: '',
  reviewText: '',
  reviewRating: null,
  reviewDate: null,
  account: Account()
})

export const Account = () =>({
  id: '',
  prename: '',
  surname: '',
  username: '',
  birthday: null,
  email: '',
  profilePicture: '',
  publisher: ''
})
