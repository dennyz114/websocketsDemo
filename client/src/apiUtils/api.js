import SockJs from 'sockjs-client'
import Stomp from 'stompjs'

const STUDENTS_API = 'student'
const WS_ENDPOINT = '/ws-students-demo'

let socketClient

export const getStudents = async () => {
  const response = await fetch(`http://localhost:3000/api/${STUDENTS_API}`)
  return response.json()
}

export const connectToWS = (userName, onConnectCallback, onMessageCallback) => {
  const socket = new SockJs(`http://localhost:3000${WS_ENDPOINT}`)
  socketClient = Stomp.over(socket)
  socketClient.connect({userName}, () => {
    onConnectCallback()
    socketClient.subscribe('/topic/students', message => {
      console.log('Message for all users from WS: ', message)
      const student = JSON.parse(message.body)
      onMessageCallback(student)
    })

    socketClient.subscribe('/users/topic/students', message => {
      console.log('Message only for me from WS: ', message)
    })
  })
}

export const closeWS = () => {
  if( socketClient ) {
    console.log('Disconnected from socket')
    socketClient.disconnect()
  }
}

export const sendHiToUser = (userNameToReceiveHello) => {
  if (socketClient !== null) {
    socketClient.send('/app/hello', {}, JSON.stringify({name: userNameToReceiveHello}))
  }
}