export const gameDto = () => ({
  id: '',
  title: '',
  description:'',
  platforms: [],
  genres: [],
  publicationDate: '',
  ageRating: '',
  developer: '',
  publisher: '',
  gameImage: '',
  reviewsDto: [reviewsDto()]
})

export const reviewsDto = () => ({
  id: '',
  reviewHeadline: '',
  reviewText: '',
  rating: '',
  reviewDate: '',
  accountDto: accountDto()
})

export const accountDto = () =>({
  id: '',
  prename: '',
  surname: '',
  username: '',
  birthday: '',
  email: '',
  profilePicture: '',
  publisher: ''
})
